package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void testConvert_FeetToInches() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);
        assertEquals(12.0, q.convertTo(LengthUnit.INCHES).getValue(), 0.01);
    }

    @Test
    void testEquality_CrossUnit() {
        QuantityLength a = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength b = new QuantityLength(1.0, LengthUnit.FEET);
        assertTrue(a.equals(b));
    }

    @Test
    void testAdd_TargetFeet() {
        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);
        assertEquals(2.0, a.add(b, LengthUnit.FEET).getValue(), 0.01);
    }

    @Test
    void testAdd_TargetInches() {
        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);
        assertEquals(24.0, a.add(b, LengthUnit.INCHES).getValue(), 0.01);
    }

    @Test
    void testAdd_TargetYards() {
        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);
        assertEquals(0.67, a.add(b, LengthUnit.YARDS).getValue(), 0.01);
    }

    @Test
    void testAdd_Centimeter() {
        QuantityLength a = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength b = new QuantityLength(1.0, LengthUnit.INCHES);
        assertEquals(5.08, a.add(b, LengthUnit.CENTIMETERS).getValue(), 0.01);
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityLength(1.0, null));
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
    }
}