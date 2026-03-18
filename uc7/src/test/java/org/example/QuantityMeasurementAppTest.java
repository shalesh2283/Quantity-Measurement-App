package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

    @Test
    void testAddition_Target_Feet() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        QuantityMeasurementApp.QuantityLength result =
                q1.add(q2, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), 1e-6);
    }

    @Test
    void testAddition_Target_Inches() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        QuantityMeasurementApp.QuantityLength result =
                q1.add(q2, QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), 1e-6);
    }

    @Test
    void testAddition_Target_Yards() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        QuantityMeasurementApp.QuantityLength result =
                q1.add(q2, QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(0.6667, result.getValue(), 0.01);
    }

    @Test
    void testAddition_Target_Centimeters() {
        QuantityMeasurementApp.QuantityLength result =
                QuantityMeasurementApp.add(1.0,
                        QuantityMeasurementApp.LengthUnit.INCHES,
                        1.0,
                        QuantityMeasurementApp.LengthUnit.INCHES,
                        QuantityMeasurementApp.LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 0.01);
    }

    @Test
    void testAddition_Commutativity() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        double r1 = q1.add(q2, QuantityMeasurementApp.LengthUnit.YARDS).getValue();
        double r2 = q2.add(q1, QuantityMeasurementApp.LengthUnit.YARDS).getValue();

        assertEquals(r1, r2, 1e-6);
    }

    @Test
    void testAddition_NullTarget() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
    }

    @Test
    void testAddition_WithZero() {
        QuantityMeasurementApp.QuantityLength result =
                QuantityMeasurementApp.add(5.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        0.0,
                        QuantityMeasurementApp.LengthUnit.INCHES,
                        QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(1.6667, result.getValue(), 0.01);
    }

    @Test
    void testAddition_Negative() {
        QuantityMeasurementApp.QuantityLength result =
                QuantityMeasurementApp.add(5.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        -2.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(36.0, result.getValue(), 1e-6);
    }
}