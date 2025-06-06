package com.ameltaleb.pricing.domain.model.aggregate;

import java.math.BigDecimal;
import java.util.Objects;

import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;

public record Price(
    BrandId brandId,
    ProductId productId,
    PriceRange applicableRange,
    Integer priceList,
    Integer priority,
    BigDecimal amount,
    LocalCurrency currency
) {
    
    public Price {
        Objects.requireNonNull(brandId, "BrandId cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (priority == null || priority < 0) {
            throw new IllegalArgumentException("Priority must be >= 0");
        }
    }
}