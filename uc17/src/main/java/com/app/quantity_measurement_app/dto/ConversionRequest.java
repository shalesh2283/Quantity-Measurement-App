package com.app.quantity_measurement_app.dto;

import jakarta.validation.constraints.*;

public class ConversionRequest {

    @NotNull
    private Double value;

    @NotBlank
    private String fromUnit;

    @NotBlank
    private String toUnit;

    public Double getValue() { return value; }
    public String getFromUnit() { return fromUnit; }
    public String getToUnit() { return toUnit; }

    public void setValue(Double value) { this.value = value; }
    public void setFromUnit(String fromUnit) { this.fromUnit = fromUnit; }
    public void setToUnit(String toUnit) { this.toUnit = toUnit; }
}