package com.ameltaleb.pricing.application.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class PriceResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void shouldSerializeToJson() throws JsonProcessingException {
        PriceResponse response = new PriceResponse(
                35455,
                1,
                2,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                new BigDecimal("25.45"),
                "EUR"
        );

        String json = objectMapper.writeValueAsString(response);

        assertTrue(json.contains("\"productId\":35455"));
        assertTrue(json.contains("\"brandId\":1"));
        assertTrue(json.contains("\"priceList\":2"));
        assertTrue(json.contains("2020-06-14 15:00:00"));
        assertTrue(json.contains("2020-06-14 18:30:00"));
        assertTrue(json.contains("25.45"));
        assertTrue(json.contains("\"currency\":\"EUR\""));
    }

    @Test
    void shouldCreateUsingFactoryMethod() {
        PriceResponse response = PriceResponse.fromDomain(
                35455, 1, 2,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                new BigDecimal("25.45"),
                "EUR"
        );

        assertEquals(35455, response.productId());
        assertEquals(1, response.brandId());
        assertEquals(2, response.priceList());
        assertEquals("EUR", response.currency());
    }
}