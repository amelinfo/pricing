package com.ameltaleb.pricing.domain.model.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;

public class LocalCurrencyTest {

    @Test
    void rejectsInvalidCurrency() {
        CurrencyValidationPort mockValidator = mock(CurrencyValidationPort.class);
        when(mockValidator.isValid("XXX")).thenReturn(false);

        assertThrows(
            IllegalArgumentException.class,
            () -> LocalCurrency.validate("XXX", mockValidator)
        );
    }

    @Test
    void acceptValidCurrency(){
                CurrencyValidationPort mockValidator = mock(CurrencyValidationPort.class);
        when(mockValidator.isValid("EUR")).thenReturn(true);

        assertDoesNotThrow( ()->LocalCurrency.validate("EUR", mockValidator));
    }

}

