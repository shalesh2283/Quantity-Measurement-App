package org.example.Enums;

import org.example.Interface.IMeasurable;

public enum WeightUnit implements IMeasurable {
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    OUNCE(28.3495);

    private final double toGramFactor;

    WeightUnit(double toGramFactor) {
        this.toGramFactor = toGramFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * toGramFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toGramFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public boolean supportsArithmetic() {
        return true;
    }

    @Override
    public String getMeasurementType() {
        return "WEIGHT";
    }
}