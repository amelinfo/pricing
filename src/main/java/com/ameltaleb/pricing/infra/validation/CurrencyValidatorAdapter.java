package com.ameltaleb.pricing.infra.validation;

import java.util.Currency;

import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;

public class CurrencyValidatorAdapter implements CurrencyValidationPort {
    @Override
    public boolean isValid(String code) {
        try {
            Currency.getInstance(code);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
