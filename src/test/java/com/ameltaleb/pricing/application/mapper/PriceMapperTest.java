package com.ameltaleb.pricing.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.ameltaleb.pricing.application.dto.PriceResponse;
import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;

class PriceMapperTest {

    @Test
    void shouldMapPriceToPriceResponse() {
        Price price = new Price(
            new BrandId(1),
            new ProductId(35455),
            new PriceRange(
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 6, 14, 23, 59)
            ),
            1,
            0,
            new BigDecimal("35.50"),
            new LocalCurrency("EUR", code -> true)
        );

        PriceResponse response = PriceMapper.toResponse(price);

        assertNotNull(response);
        assertEquals(35455, response.productId());
        assertEquals(1, response.brandId());
        assertEquals(1, response.priceList());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), response.startDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 23, 59), response.endDate());
        assertEquals(new BigDecimal("35.50"), response.price());
        assertEquals("EUR", response.currency());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> PriceMapper.toResponse(null));
        assertEquals("Price cannot be null", ex.getMessage());
    }
}
