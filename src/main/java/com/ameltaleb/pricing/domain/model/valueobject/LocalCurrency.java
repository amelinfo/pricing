package com.ameltaleb.pricing.domain.model.valueobject;

import java.util.Currency;


public record LocalCurrency(String currencyCode) {
    public LocalCurrency {
        if (currencyCode == null || !isValidCurrency(currencyCode)) {
            throw new IllegalArgumentException("Invalid currency code");
        }
    }

    private boolean isValidCurrency(String currencyCode) {
        // TODO Auto-generated method stub
        try {
            Currency.getInstance(currencyCode);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
}
