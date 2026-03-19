package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testEquality() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testConversion() {
        QuantityWeight w = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight result = w.convertTo(WeightUnit.GRAM);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight result = w1.add(w2);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAdditionWithTarget() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight result = w1.add(w2, WeightUnit.GRAM);
        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    @Test
    void testPoundToKg() {
        QuantityWeight w = new QuantityWeight(2.20462, WeightUnit.POUND);
        QuantityWeight result = w.convertTo(WeightUnit.KILOGRAM);
        assertEquals(1.0, result.getValue(), 1e-3);
    }
}