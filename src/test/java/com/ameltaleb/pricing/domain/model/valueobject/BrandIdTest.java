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
    void rejectsNullBrandId() {
        assertThrows(IllegalArgumentException.class, () -> new BrandId(null));
    }

    // ---- Record Behavior ----
    @Test
    void hasValueEquality() {
        BrandId id1 = new BrandId(1);
        BrandId id2 = new BrandId(1);
        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void hasToString() {
        BrandId brandId = new BrandId(1);
        assertEquals("BrandId[value=1]", brandId.toString());
    }
}
