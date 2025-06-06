package com.ameltaleb.pricing.domain.model.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProductIdTest {

    @Test
    void shouldCreateValidProductIdUsingOfFactoryMethod() {
        ProductId productId = ProductId.of(35455);
        assertEquals(35455, productId.value());
        assertEquals("ProductId{35455}", productId.toString());
    }

    @Test
    void shouldThrowExceptionIfValueIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new ProductId(null));
        assertEquals("Product ID must be positive", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionIfValueIsZeroOrNegative() {
        assertThrows(IllegalArgumentException.class, () -> new ProductId(0));
        assertThrows(IllegalArgumentException.class, () -> new ProductId(-10));
    }

    @Test
    void shouldSupportEquality() {
        ProductId p1 = new ProductId(100);
        ProductId p2 = new ProductId(100);
        ProductId p3 = new ProductId(200);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
