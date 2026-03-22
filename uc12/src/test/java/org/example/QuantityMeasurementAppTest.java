package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testSubtraction_SameUnit() {
        assertEquals(5.0,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(5.0, LengthUnit.FEET)).getValue());
    }

    @Test
    void testSubtraction_DifferentUnit() {
        assertEquals(9.5,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCHES)).getValue(), 0.1);
    }

    @Test
    void testSubtraction_WithTargetUnit() {
        assertEquals(114.0,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES)
                        .getValue(), 0.1);
    }

    @Test
    void testSubtraction_Negative() {
        assertEquals(-5.0,
                new Quantity<>(5.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(10.0, LengthUnit.FEET)).getValue());
    }

    @Test
    void testSubtraction_Zero() {
        assertEquals(0.0,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(120.0, LengthUnit.INCHES)).getValue(), 0.1);
    }

    @Test
    void testDivision_SameUnit() {
        assertEquals(5.0,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET)));
    }

    @Test
    void testDivision_DifferentUnit() {
        assertEquals(1.0,
                new Quantity<>(24.0, LengthUnit.INCHES)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET)), 0.1);
    }

    @Test
    void testDivision_LessThanOne() {
        assertEquals(0.5,
                new Quantity<>(5.0, LengthUnit.FEET)
                        .divide(new Quantity<>(10.0, LengthUnit.FEET)));
    }

    @Test
    void testDivision_ByZero() {
        assertThrows(ArithmeticException.class, () ->
                new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    @Test
    void testSubtraction_Immutability() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
        q1.subtract(q2);
        assertEquals(10.0, q1.getValue());
    }

}
