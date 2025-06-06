package com.ameltaleb.pricing.domain.model.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class BrandIdTest {

    // ---- Valid Cases ----
    @Test
    void createsValidBrandId() {
        BrandId brandId = new BrandId(1);
        assertEquals(1, brandId.value());
    }

    // ---- Invalid Cases ----
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void rejectsNonPositiveBrandIds(int invalidId) {
        assertThrows(IllegalArgumentException.class, () -> new BrandId(invalidId));
    }

    @Test
    void shouldThrowExceptionIfValueIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new BrandId(null));
        assertEquals("Brand ID must be positive", ex.getMessage());
    }

    @Test
    void shouldNotBeZaraIfIdIsDifferent() {
        BrandId brandId = new BrandId(2);
        assertEquals(2, brandId.value());
        assertFalse(brandId.isZaraBrand());
    }

    @Test
    void shouldThrowExceptionIfValueIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> new BrandId(0));
        assertThrows(IllegalArgumentException.class, () -> new BrandId(-5));
    }
}
