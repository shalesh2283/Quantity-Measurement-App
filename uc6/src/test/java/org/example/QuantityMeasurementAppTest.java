package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

    @Test
    void testAddition_SameUnit_Feet() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(2.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(new QuantityMeasurementApp.QuantityLength(3.0, QuantityMeasurementApp.LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAddition_FeetPlusInches() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(new QuantityMeasurementApp.QuantityLength(2.0, QuantityMeasurementApp.LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAddition_InchesPlusFeet() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(12.0, QuantityMeasurementApp.LengthUnit.INCHES);
        QuantityMeasurementApp.QuantityLength q2 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(new QuantityMeasurementApp.QuantityLength(24.0, QuantityMeasurementApp.LengthUnit.INCHES), q1.add(q2));
    }

    @Test
    void testAddition_YardPlusFeet() {
        QuantityMeasurementApp.QuantityLength result =
                QuantityMeasurementApp.add(1.0,
                        QuantityMeasurementApp.LengthUnit.YARDS,
                        3.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(new QuantityMeasurementApp.QuantityLength(2.0, QuantityMeasurementApp.LengthUnit.YARDS), result);
    }

    @Test
    void testAddition_CentimeterPlusInch() {
        QuantityMeasurementApp.QuantityLength result =
                QuantityMeasurementApp.add(2.54,
                        QuantityMeasurementApp.LengthUnit.CENTIMETERS,
                        1.0,
                        QuantityMeasurementApp.LengthUnit.INCHES,
                        QuantityMeasurementApp.LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 0.01);
    }

    @Test
    void testAddition_Zero() {
        QuantityMeasurementApp.QuantityLength result =
                QuantityMeasurementApp.add(5.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        0.0,
                        QuantityMeasurementApp.LengthUnit.INCHES,
                        QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(new QuantityMeasurementApp.QuantityLength(5.0, QuantityMeasurementApp.LengthUnit.FEET), result);
    }

    @Test
    void testAddition_Negative() {
        QuantityMeasurementApp.QuantityLength result =
                QuantityMeasurementApp.add(5.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        -2.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(new QuantityMeasurementApp.QuantityLength(3.0, QuantityMeasurementApp.LengthUnit.FEET), result);
    }

    @Test
    void testAddition_Null() {
        QuantityMeasurementApp.QuantityLength q1 =
                new QuantityMeasurementApp.QuantityLength(1.0, QuantityMeasurementApp.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q1.add(null));
    }
}