package com.ameltaleb.pricing.domain.ports.input;

import java.time.LocalDateTime;
import java.util.Optional;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;

public interface PriceInputPort {
Optional<Price> findApplicablePrice(LocalDateTime applicationDate, 
                                      ProductId productId, 
                                      BrandId brandId);
}
