package org.example;
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 0.0001;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit target) {
        if (target == null) throw new IllegalArgumentException();
        double base = unit.toBase(value);
        double result = target.fromBase(base);
        return new QuantityLength(round(result), target);
    }

    public QuantityLength add(QuantityLength other, LengthUnit target) {
        if (other == null || target == null) throw new IllegalArgumentException();
        double sum = unit.toBase(value) + other.unit.toBase(other.value);
        double result = target.fromBase(sum);
        return new QuantityLength(round(result), target);
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityLength)) return false;
        QuantityLength q = (QuantityLength) o;
        double a = unit.toBase(value);
        double b = q.unit.toBase(q.value);
        return Math.abs(a - b) < EPSILON;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(a.convertTo(LengthUnit.INCHES).getValue());
        System.out.println(a.add(b, LengthUnit.FEET).getValue());
        System.out.println(a.add(b, LengthUnit.INCHES).getValue());
        System.out.println(a.add(b, LengthUnit.YARDS).getValue());
    }
}