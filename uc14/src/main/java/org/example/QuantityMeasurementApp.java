package org.example;
import java.util.function.DoubleBinaryOperator;
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

    CELSIUS(
            c -> c,
            c -> c
    ),
    FAHRENHEIT(
            f -> (f - 32) * 5 / 9,
            c -> (c * 9 / 5) + 32
    );

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

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException("Temperature does not support " + operation);
    }
}

class Quantity<U extends Enum<U> & IMeasurable<U>> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (b == 0) throw new ArithmeticException("Division by zero");
            return a / b;
        });

        private final DoubleBinaryOperator op;

        ArithmeticOperation(DoubleBinaryOperator op) {
            this.op = op;
        }

        public double compute(double a, double b) {
            return op.applyAsDouble(a, b);
        }
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetRequired, String operation) {
        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (this.unit == null || other.unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cannot operate on different unit types");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");

        if (targetRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit required");

        this.unit.validateOperationSupport(operation);
    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {
        double base1 = unit.toBaseUnit(value);
        double base2 = other.unit.toBaseUnit(other.value);
        return op.compute(base1, base2);
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    public Quantity<U> add(Quantity<U> other) {
        validateArithmeticOperands(other, this.unit, true, "addition");
        double result = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(round(unit.fromBaseUnit(result)), unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true, "addition");
        double result = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(round(targetUnit.fromBaseUnit(result)), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        validateArithmeticOperands(other, this.unit, true, "subtraction");
        double result = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(round(unit.fromBaseUnit(result)), unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true, "subtraction");
        double result = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(round(targetUnit.fromBaseUnit(result)), targetUnit);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false, "division");
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.toBaseUnit(value);
        double converted = targetUnit.fromBaseUnit(base);
        return new Quantity<>(round(converted), targetUnit);
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (this.unit.getClass() != other.unit.getClass()) return false;

        double base1 = this.unit.toBaseUnit(this.value);
        double base2 = ((IMeasurable) other.unit).toBaseUnit(other.value);

        return Math.abs(base1 - base2) < 0.01;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32, TemperatureUnit.FAHRENHEIT);

        System.out.println(t1.equals(t2));
    }
}