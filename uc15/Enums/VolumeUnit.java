package org.example.Enums;

import org.example.Interface.IMeasurable;

public enum VolumeUnit implements IMeasurable {
    LITER(1.0),
    MILLILITER(0.001),
    GALLON(3.78541);

    private final double toLiterFactor;

    VolumeUnit(double toLiterFactor) {
        this.toLiterFactor = toLiterFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * toLiterFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toLiterFactor;
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
        return "VOLUME";
    }
}