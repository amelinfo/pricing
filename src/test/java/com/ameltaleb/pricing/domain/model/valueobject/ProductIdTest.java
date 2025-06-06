package com.ameltaleb.pricing.domain.model.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductIdTest {

    @Test
    void createsValidProductId() {
        ProductId productId = new ProductId(35455L);
        assertEquals(35455L, productId.value());
    }

    @ParameterizedTest
    @ValueSource(longs = {0L, -1L})
    void rejectsNonPositiveProductIds(long invalidId) {
        assertThrows(IllegalArgumentException.class, () -> new ProductId(invalidId));
    }

    @Test
    void rejectsNullProductId() {
        assertThrows(IllegalArgumentException.class, () -> new ProductId(null));
    }

    // ... (equality/toString tests similar to BrandId)
}
