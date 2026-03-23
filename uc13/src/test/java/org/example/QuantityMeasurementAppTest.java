package org.example;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void testAdd_FeetAndInch() {
        Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.add(q2);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testSubtract() {
        Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(9.5, result.getValue());
    }

    @Test
    void testDivide() {
        Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2, LengthUnit.FEET);

        double result = q1.divide(q2);

        assertEquals(5.0, result);
    }

    @Test
    void testAddWithTargetUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCH);

        Quantity<LengthUnit> result = q1.add(q2, LengthUnit.INCH);

        assertEquals(24.0, result.getValue());
    }

    @Test
    void testNullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q1.add(null));
    }

    @Test
    void testDifferentUnits() {
        Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add((Quantity) new Quantity<>(1, null)));
    }

    @Test
    void testDivideByZero() {
        Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }
}