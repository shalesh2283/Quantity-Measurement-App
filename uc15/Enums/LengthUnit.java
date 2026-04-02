package org.example.Enums;

import org.example.Interface.IMeasurable;

public enum LengthUnit implements IMeasurable {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(1.0 / 30.48),
    METER(100.0 / 30.48);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
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
        return "LENGTH";
    }
}