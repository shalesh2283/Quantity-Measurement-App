package org.example;


import java.util.function.DoubleBinaryOperator;

interface IMeasurable<U> {
    double toBaseUnit(double value);
    double fromBaseUnit(double baseValue);
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

class Quantity<U extends Enum<U> & IMeasurable<U>> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
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

        private final DoubleBinaryOperator operation;

        ArithmeticOperation(DoubleBinaryOperator operation) {
            this.operation = operation;
        }

        public double compute(double a, double b) {
            return operation.applyAsDouble(a, b);
        }
    }
    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetRequired) {
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
        validateArithmeticOperands(other, this.unit, true);
        double result = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double finalValue = unit.fromBaseUnit(result);
        return new Quantity<>(round(finalValue), unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double result = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double finalValue = targetUnit.fromBaseUnit(result);
        return new Quantity<>(round(finalValue), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        validateArithmeticOperands(other, this.unit, true);
        double result = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double finalValue = unit.fromBaseUnit(result);
        return new Quantity<>(round(finalValue), unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double result = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double finalValue = targetUnit.fromBaseUnit(result);
        return new Quantity<>(round(finalValue), targetUnit);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }
}

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.add(q2);
        System.out.println(result.getValue());
    }
}