package com.app.quantity_measurement_app.model;

import jakarta.persistence.*;

@Entity
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double inputValue;
    private String fromUnit;
    private String toUnit;
    private double result;

    public Conversion() {}

    public Conversion(double inputValue, String fromUnit, String toUnit, double result) {
        this.inputValue = inputValue;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.result = result;
    }

    public Long getId() { return id; }

    public double getInputValue() { return inputValue; }
    public String getFromUnit() { return fromUnit; }
    public String getToUnit() { return toUnit; }
    public double getResult() { return result; }

    public void setId(Long id) { this.id = id; }
    public void setInputValue(double inputValue) { this.inputValue = inputValue; }
    public void setFromUnit(String fromUnit) { this.fromUnit = fromUnit; }
    public void setToUnit(String toUnit) { this.toUnit = toUnit; }
    public void setResult(double result) { this.result = result; }
}