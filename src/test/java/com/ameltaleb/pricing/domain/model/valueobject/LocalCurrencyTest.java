package com.ameltaleb.pricing.domain.model.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;

public class LocalCurrencyTest {

    private final CurrencyValidationPort validValidator = code -> code != null && code.matches("[A-Z]{3}");
    private final CurrencyValidationPort invalidValidator = code -> false;

    @Test
    void shouldCreateValidCurrency() {
        LocalCurrency currency = LocalCurrency.validate("EUR", validValidator);
        assertEquals("EUR", currency.currencyCode());
    }

    @Test
    void shouldThrowExceptionForInvalidCurrencyCode() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            LocalCurrency.validate("INVALID", invalidValidator)
        );
        assertEquals("Invalid currency code", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullCurrencyCode() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            LocalCurrency.validate(null, validValidator)
        );
        assertEquals("Invalid currency code", ex.getMessage());
    }

}

