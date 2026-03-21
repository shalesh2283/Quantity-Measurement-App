package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {
    @Test
    void testEquality_LitreAndMillilitre() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testEquality_LitreAndGallon() {
        assertTrue(new Quantity<>(3.78541, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.GALLON)));
    }

    @Test
    void testConversion_LitreToMillilitre() {
        assertEquals(1000.0,
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE).getValue());
    }

    @Test
    void testConversion_GallonToLitre() {
        assertEquals(3.78541,
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.LITRE).getValue(), 0.01);
    }

    @Test
    void testAddition_SameUnit() {
        assertEquals(3.0,
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(2.0, VolumeUnit.LITRE)).getValue());
    }

    @Test
    void testAddition_DifferentUnit() {
        assertEquals(2.0,
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)).getValue());
    }

    @Test
    void testAddition_WithTargetUnit() {
        assertEquals(4785.41,
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1.0, VolumeUnit.GALLON), VolumeUnit.MILLILITRE)
                        .getValue(), 0.1);
    }

    @Test
    void testDifferentTypes_ShouldReturnFalse() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, LengthUnit.FEET)));
    }

    @Test
    void testZeroValues() {
        assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testNegativeValues() {
        assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
    }


}
