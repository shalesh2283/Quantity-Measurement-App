package org.example;
public class QuantityMeasurementApp {

    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value) || unit == null) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        private double toBase() {
            return value * unit.getFactor();
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException();
            }
            double base = toBase();
            double result = base / targetUnit.getFactor();
            return new QuantityLength(result, targetUnit);
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException();
            }
            double sum = this.toBase() + other.toBase();
            double result = sum / this.unit.getFactor();
            return new QuantityLength(result, this.unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException();
            }
            double sum = this.toBase() + other.toBase();
            double result = sum / targetUnit.getFactor();
            return new QuantityLength(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toBase() - other.toBase()) < 1e-6;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static QuantityLength add(QuantityLength l1, QuantityLength l2, LengthUnit targetUnit) {
        if (l1 == null || l2 == null || targetUnit == null) {
            throw new IllegalArgumentException();
        }
        double sum = l1.toBase() + l2.toBase();
        double result = sum / targetUnit.getFactor();
        return new QuantityLength(result, targetUnit);
    }

    public static QuantityLength add(double v1, LengthUnit u1, double v2, LengthUnit u2, LengthUnit targetUnit) {
        if (!Double.isFinite(v1) || !Double.isFinite(v2) || u1 == null || u2 == null || targetUnit == null) {
            throw new IllegalArgumentException();
        }
        double base1 = v1 * u1.getFactor();
        double base2 = v2 * u2.getFactor();
        double sum = base1 + base2;
        double result = sum / targetUnit.getFactor();
        return new QuantityLength(result, targetUnit);
    }
}