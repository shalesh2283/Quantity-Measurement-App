package org.example;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@FunctionalInterface
interface SupportsArithmetic {
    boolean isSupported();
}

interface IMeasurable<U> {
    double toBaseUnit(double value);
    double fromBaseUnit(double baseValue);

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
    }
}

enum LengthUnit implements IMeasurable<LengthUnit> {
    FEET(12.0), INCH(1.0);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBaseUnit(double value) {
        return value * factor;
    }

    public double fromBaseUnit(double baseValue) {
        return baseValue / factor;
    }
}

enum TemperatureUnit implements IMeasurable<TemperatureUnit> {

    CELSIUS(c -> c, c -> c),
    FAHRENHEIT(f -> (f - 32) * 5 / 9, c -> (c * 9 / 5) + 32);

    private final Function<Double, Double> toBase;
    private final Function<Double, Double> fromBase;

    private final SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(Function<Double, Double> toBase, Function<Double, Double> fromBase) {
        this.toBase = toBase;
        this.fromBase = fromBase;
    }

    public double toBaseUnit(double value) {
        return toBase.apply(value);
    }

    public double fromBaseUnit(double baseValue) {
        return fromBase.apply(baseValue);
    }

    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException("Temperature does not support " + operation);
    }
}

class QuantityModel<U extends Enum<U> & IMeasurable<U>> {
    private final double value;
    private final U unit;

    public QuantityModel(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }
}

class QuantityDTO {
    private double value;
    private String unit;
    private String type;

    public QuantityDTO(double value, String unit, String type) {
        this.value = value;
        this.unit = unit;
        this.type = type;
    }

    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public String getType() { return type; }
}

class QuantityMeasurementEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private QuantityDTO operand1;
    private QuantityDTO operand2;
    private QuantityDTO result;
    private String operation;
    private boolean error;
    private String errorMessage;

    public QuantityMeasurementEntity(QuantityDTO op1, QuantityDTO op2, QuantityDTO result, String operation) {
        this.operand1 = op1;
        this.operand2 = op2;
        this.result = result;
        this.operation = operation;
        this.error = false;
    }

    public QuantityMeasurementEntity(String errorMessage) {
        this.error = true;
        this.errorMessage = errorMessage;
    }

    public boolean hasError() { return error; }
    public QuantityDTO getResult() { return result; }
    public String getErrorMessage() { return errorMessage; }
}

interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);
    List<QuantityMeasurementEntity> findAll();
}

class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final QuantityMeasurementCacheRepository INSTANCE = new QuantityMeasurementCacheRepository();
    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {}

    public static QuantityMeasurementCacheRepository getInstance() {
        return INSTANCE;
    }

    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
    }

    public List<QuantityMeasurementEntity> findAll() {
        return cache;
    }
}

class QuantityMeasurementException extends RuntimeException {
    public QuantityMeasurementException(String msg) {
        super(msg);
    }
}

interface IQuantityMeasurementService {
    QuantityMeasurementEntity compare(QuantityDTO q1, QuantityDTO q2);
    QuantityMeasurementEntity convert(QuantityDTO q, String targetUnit);
    QuantityMeasurementEntity add(QuantityDTO q1, QuantityDTO q2);
}

class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private <U extends Enum<U> & IMeasurable<U>> QuantityModel<U> toModel(QuantityDTO dto, Class<U> enumClass) {
        U unit = Enum.valueOf(enumClass, dto.getUnit());
        return new QuantityModel<>(dto.getValue(), unit);
    }

    public QuantityMeasurementEntity compare(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getType().equals(q2.getType()))
                throw new QuantityMeasurementException("Different types");

            if (q1.getType().equals("TEMPERATURE")) {
                QuantityModel<TemperatureUnit> m1 = toModel(q1, TemperatureUnit.class);
                QuantityModel<TemperatureUnit> m2 = toModel(q2, TemperatureUnit.class);

                double b1 = m1.getUnit().toBaseUnit(m1.getValue());
                double b2 = m2.getUnit().toBaseUnit(m2.getValue());

                boolean res = Math.abs(b1 - b2) < 0.01;
                QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2,
                        new QuantityDTO(res ? 1 : 0, "BOOLEAN", "BOOLEAN"), "COMPARE");
                repository.save(e);
                return e;
            }

            QuantityModel<LengthUnit> m1 = toModel(q1, LengthUnit.class);
            QuantityModel<LengthUnit> m2 = toModel(q2, LengthUnit.class);

            double b1 = m1.getUnit().toBaseUnit(m1.getValue());
            double b2 = m2.getUnit().toBaseUnit(m2.getValue());

            boolean res = Math.abs(b1 - b2) < 0.01;

            QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2,
                    new QuantityDTO(res ? 1 : 0, "BOOLEAN", "BOOLEAN"), "COMPARE");
            repository.save(e);
            return e;

        } catch (Exception e) {
            return new QuantityMeasurementEntity(e.getMessage());
        }
    }

    public QuantityMeasurementEntity convert(QuantityDTO q, String targetUnit) {
        try {
            if (q.getType().equals("TEMPERATURE")) {
                QuantityModel<TemperatureUnit> m = toModel(q, TemperatureUnit.class);
                TemperatureUnit target = TemperatureUnit.valueOf(targetUnit);

                double base = m.getUnit().toBaseUnit(m.getValue());
                double result = target.fromBaseUnit(base);

                QuantityDTO res = new QuantityDTO(result, target.name(), "TEMPERATURE");
                QuantityMeasurementEntity e = new QuantityMeasurementEntity(q, null, res, "CONVERT");
                repository.save(e);
                return e;
            }

            QuantityModel<LengthUnit> m = toModel(q, LengthUnit.class);
            LengthUnit target = LengthUnit.valueOf(targetUnit);

            double base = m.getUnit().toBaseUnit(m.getValue());
            double result = target.fromBaseUnit(base);

            QuantityDTO res = new QuantityDTO(result, target.name(), q.getType());
            QuantityMeasurementEntity e = new QuantityMeasurementEntity(q, null, res, "CONVERT");
            repository.save(e);
            return e;

        } catch (Exception e) {
            return new QuantityMeasurementEntity(e.getMessage());
        }
    }

    public QuantityMeasurementEntity add(QuantityDTO q1, QuantityDTO q2) {
        try {
            if (!q1.getType().equals(q2.getType()))
                throw new QuantityMeasurementException("Different types");

            if (q1.getType().equals("TEMPERATURE"))
                throw new UnsupportedOperationException("Temperature does not support addition");

            QuantityModel<LengthUnit> m1 = toModel(q1, LengthUnit.class);
            QuantityModel<LengthUnit> m2 = toModel(q2, LengthUnit.class);

            double b1 = m1.getUnit().toBaseUnit(m1.getValue());
            double b2 = m2.getUnit().toBaseUnit(m2.getValue());

            double result = b1 + b2;
            double finalVal = m1.getUnit().fromBaseUnit(result);

            QuantityDTO res = new QuantityDTO(finalVal, m1.getUnit().name(), q1.getType());
            QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2, res, "ADD");
            repository.save(e);
            return e;

        } catch (Exception e) {
            return new QuantityMeasurementEntity(e.getMessage());
        }
    }
}

class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performCompare(QuantityDTO q1, QuantityDTO q2) {
        display(service.compare(q1, q2));
    }

    public void performConvert(QuantityDTO q, String targetUnit) {
        display(service.convert(q, targetUnit));
    }

    public void performAdd(QuantityDTO q1, QuantityDTO q2) {
        display(service.add(q1, q2));
    }

    private void display(QuantityMeasurementEntity e) {
        if (e.hasError()) System.out.println("Error: " + e.getErrorMessage());
        else System.out.println("Result: " + e.getResult().getValue() + " " + e.getResult().getUnit());
    }
}

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        IQuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repo);
        QuantityMeasurementController controller = new QuantityMeasurementController(service);

        QuantityDTO t1 = new QuantityDTO(0, "CELSIUS", "TEMPERATURE");
        QuantityDTO t2 = new QuantityDTO(32, "FAHRENHEIT", "TEMPERATURE");

        controller.performCompare(t1, t2);
        controller.performConvert(t1, "FAHRENHEIT");

        controller.performAdd(t1, t2);
    }
}