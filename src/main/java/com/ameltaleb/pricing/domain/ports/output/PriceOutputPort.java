package com.ameltaleb.pricing.domain.ports.output;

import java.time.LocalDateTime;
import java.util.Optional;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;

public interface PriceOutputPort {
    Optional<Price> findPriceByBrandProductAndDate(BrandId brandId, ProductId productId, LocalDateTime date);
}
