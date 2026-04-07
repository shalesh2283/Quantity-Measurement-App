package com.app.quantity_measurement_app.dto;

public class ConversionResponse {

    private double result;
    private String message;

    public ConversionResponse(double result, String message) {
        this.result = result;
        this.message = message;
    }

    public double getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}