package com.ameltaleb.pricing.domain.ports.output;

public interface CurrencyValidationPort {
    boolean isValid(String currencyCode);
}
