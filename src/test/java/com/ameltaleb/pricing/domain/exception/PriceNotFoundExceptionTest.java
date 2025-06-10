package com.ameltaleb.pricing.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PriceNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultMessage() {
        PriceNotFoundException ex = new PriceNotFoundException();
        assertEquals("Price not found for given criteria", ex.getMessage());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        PriceNotFoundException ex = new PriceNotFoundException("Custom message");
        assertEquals("Custom message", ex.getMessage());
    }

    @Test
    void shouldCreateExceptionWithCustomMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        PriceNotFoundException ex = new PriceNotFoundException("Message with cause", cause);
        assertEquals("Message with cause", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
