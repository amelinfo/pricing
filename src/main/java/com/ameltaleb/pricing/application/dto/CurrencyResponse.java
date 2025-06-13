package com.ameltaleb.pricing.application.dto;

import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;

public record CurrencyResponse(String code) {
    public CurrencyResponse(LocalCurrency currency) {
        this(currency.currencyCode());
    }
}
