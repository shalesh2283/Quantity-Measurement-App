package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void testTemperatureEquality() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32, TemperatureUnit.FAHRENHEIT);

        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureConversion() {
        Quantity<TemperatureUnit> t = new Quantity<>(100, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result = t.convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(212.0, result.getValue());
    }

    @Test
    void testUnsupportedAdd() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50, TemperatureUnit.CELSIUS);

        assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
    }

    @Test
    void testUnsupportedSubtract() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50, TemperatureUnit.CELSIUS);

        assertThrows(UnsupportedOperationException.class, () -> t1.subtract(t2));
    }

    @Test
    void testUnsupportedDivide() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50, TemperatureUnit.CELSIUS);

        assertThrows(UnsupportedOperationException.class, () -> t1.divide(t2));
    }

    @Test
    void testCrossCategoryComparison() {
        Quantity<TemperatureUnit> t = new Quantity<>(100, TemperatureUnit.CELSIUS);
        Quantity<LengthUnit> l = new Quantity<>(100, LengthUnit.FEET);

        assertFalse(t.equals(l));
    }
}