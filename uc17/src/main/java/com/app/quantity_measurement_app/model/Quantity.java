package com.app.quantity_measurement_app.model;

import jakarta.persistence.*;

@Entity
public class Quantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;
    private String unit;

    public Quantity() {}

    public Quantity(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}