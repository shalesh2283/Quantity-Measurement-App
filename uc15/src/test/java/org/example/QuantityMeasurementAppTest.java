package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceTest {

    private IQuantityMeasurementService service;

    @BeforeEach
    void setup() {
        service = new QuantityMeasurementServiceImpl(
                QuantityMeasurementCacheRepository.getInstance()
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit() {
        QuantityDTO q1 = new QuantityDTO(0.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO q2 = new QuantityDTO(32.0, "FAHRENHEIT", "TEMPERATURE");

        QuantityMeasurementEntity result = service.compare(q1, q2);

        assertFalse(result.hasError());
        assertEquals(1.0, result.getResult().getValue());
    }

    @Test
    void testTemperatureEquality_KelvinToCelsius() {
        QuantityDTO q1 = new QuantityDTO(273.15, "CELSIUS", "TEMPERATURE");
        QuantityDTO q2 = new QuantityDTO(273.15, "CELSIUS", "TEMPERATURE");

        QuantityMeasurementEntity result = service.compare(q1, q2);

        assertFalse(result.hasError());
        assertEquals(1.0, result.getResult().getValue());
    }

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit() {
        QuantityDTO q = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");

        QuantityMeasurementEntity result = service.convert(q, "FAHRENHEIT");

        assertFalse(result.hasError());
        assertEquals(212.0, result.getResult().getValue(), 0.01);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius() {
        QuantityDTO q = new QuantityDTO(32.0, "FAHRENHEIT", "TEMPERATURE");

        QuantityMeasurementEntity result = service.convert(q, "CELSIUS");

        assertFalse(result.hasError());
        assertEquals(0.0, result.getResult().getValue(), 0.01);
    }

    @Test
    void testTemperatureConversion_Negative40() {
        QuantityDTO q = new QuantityDTO(-40.0, "CELSIUS", "TEMPERATURE");

        QuantityMeasurementEntity result = service.convert(q, "FAHRENHEIT");

        assertFalse(result.hasError());
        assertEquals(-40.0, result.getResult().getValue(), 0.01);
    }

    @Test
    void testUnsupportedAddition_Temperature() {
        QuantityDTO q1 = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO q2 = new QuantityDTO(50.0, "CELSIUS", "TEMPERATURE");

        QuantityMeasurementEntity result = service.add(q1, q2);

        assertTrue(result.hasError());
        assertTrue(result.getErrorMessage().contains("Temperature does not support addition"));
    }

    @Test
    void testCrossCategoryComparison() {
        QuantityDTO temp = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO length = new QuantityDTO(100.0, "FEET", "LENGTH");

        QuantityMeasurementEntity result = service.compare(temp, length);

        assertTrue(result.hasError());
        assertTrue(result.getErrorMessage().contains("Different types"));
    }

    @Test
    void testLengthAddition() {
        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCH", "LENGTH");

        QuantityMeasurementEntity result = service.add(q1, q2);

        assertFalse(result.hasError());
        assertEquals(2.0, result.getResult().getValue(), 0.01);
    }

    @Test
    void testLengthConversion() {
        QuantityDTO q = new QuantityDTO(1.0, "FEET", "LENGTH");

        QuantityMeasurementEntity result = service.convert(q, "INCH");

        assertFalse(result.hasError());
        assertEquals(12.0, result.getResult().getValue(), 0.01);
    }

    @Test
    void testSameUnitConversion() {
        QuantityDTO q = new QuantityDTO(50.0, "CELSIUS", "TEMPERATURE");

        QuantityMeasurementEntity result = service.convert(q, "CELSIUS");

        assertFalse(result.hasError());
        assertEquals(50.0, result.getResult().getValue(), 0.01);
    }

    @Test
    void testInvalidUnit() {
        QuantityDTO q = new QuantityDTO(50.0, "INVALID", "TEMPERATURE");

        QuantityMeasurementEntity result = service.convert(q, "CELSIUS");

        assertTrue(result.hasError());
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityModel<>(100.0, null));
    }
}