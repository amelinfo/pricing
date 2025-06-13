package com.ameltaleb.pricing.application.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;
import com.ameltaleb.pricing.infra.validation.CurrencyValidatorAdapter;

class PriceResponseTest {

    @TestConfiguration
    static class MockConfig {
        @Bean
        public PriceInputPort priceInputPort() {
            return Mockito.mock(PriceInputPort.class);
        }
    }

    @Test
    void shouldMapAllFieldsCorrectly() {
        // Given
        Price price = new Price(
            new BrandId(1),
            new ProductId(35455),
            new PriceRange(
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59)
            ),
            1, // priceList
            0, // priority
            new BigDecimal(35.50),
            new LocalCurrency("EUR", new CurrencyValidatorAdapter())
        );

        // When
        PriceResponse response = new PriceResponse(price);

        // Then
        assertAll(
            () -> assertEquals(1, response.brandId().value()),
            () -> assertEquals(35455, response.productId().value()),
            () -> assertEquals(1, response.priceList()),
            () -> assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), response.startDate()),
            () -> assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), response.endDate()),
            () -> assertEquals(BigDecimal.valueOf(35.50), response.price()),
            () -> assertEquals("EUR", response.currency().code())
        );
    }
}