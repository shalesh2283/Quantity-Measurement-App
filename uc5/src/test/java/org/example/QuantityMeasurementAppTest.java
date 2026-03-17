package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

    @Test
    void testConversion_FeetToInches() {
        assertEquals(12.0, QuantityMeasurementApp.convert(1.0,
                QuantityMeasurementApp.LengthUnit.FEET,
                QuantityMeasurementApp.LengthUnit.INCHES), 1e-6);
    }

    @Test
    void testConversion_InchesToFeet() {
        assertEquals(2.0, QuantityMeasurementApp.convert(24.0,
                QuantityMeasurementApp.LengthUnit.INCHES,
                QuantityMeasurementApp.LengthUnit.FEET), 1e-6);
    }

    @Test
    void testConversion_YardsToInches() {
        assertEquals(36.0, QuantityMeasurementApp.convert(1.0,
                QuantityMeasurementApp.LengthUnit.YARDS,
                QuantityMeasurementApp.LengthUnit.INCHES), 1e-6);
    }

    @Test
    void testConversion_CentimetersToInches() {
        assertEquals(1.0, QuantityMeasurementApp.convert(2.54,
                QuantityMeasurementApp.LengthUnit.CENTIMETERS,
                QuantityMeasurementApp.LengthUnit.INCHES), 1e-3);
    }

    @Test
    void testConversion_Zero() {
        assertEquals(0.0, QuantityMeasurementApp.convert(0.0,
                QuantityMeasurementApp.LengthUnit.FEET,
                QuantityMeasurementApp.LengthUnit.INCHES), 1e-6);
    }

    @Test
    void testConversion_Negative() {
        assertEquals(-12.0, QuantityMeasurementApp.convert(-1.0,
                QuantityMeasurementApp.LengthUnit.FEET,
                QuantityMeasurementApp.LengthUnit.INCHES), 1e-6);
    }

    @Test
    void testConversion_SameUnit() {
        assertEquals(5.0, QuantityMeasurementApp.convert(5.0,
                QuantityMeasurementApp.LengthUnit.FEET,
                QuantityMeasurementApp.LengthUnit.FEET), 1e-6);
    }

    @Test
    void testConversion_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityMeasurementApp.convert(1.0, null,
                        QuantityMeasurementApp.LengthUnit.FEET));
    }

    @Test
    void testConversion_NaN() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityMeasurementApp.convert(Double.NaN,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.INCHES));
    }

    @Test
    void testConversion_RoundTrip() {
        double value = 5.0;
        double converted = QuantityMeasurementApp.convert(value,
                QuantityMeasurementApp.LengthUnit.FEET,
                QuantityMeasurementApp.LengthUnit.INCHES);

        double back = QuantityMeasurementApp.convert(converted,
                QuantityMeasurementApp.LengthUnit.INCHES,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(value, back, 1e-6);
    }
}