package com.ameltaleb.pricing.domain.model.valueobject;

import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;


public record LocalCurrency(String currencyCode, CurrencyValidationPort validator){
    public LocalCurrency {
        if (!validator.isValid(currencyCode)) {
                throw new IllegalArgumentException("Invalid currency code");
        }
    }
    // Shortcut constructor (hides validator dependency)
    public static LocalCurrency validate(String code, CurrencyValidationPort validator){
        return new LocalCurrency(code, validator);
    }
}
