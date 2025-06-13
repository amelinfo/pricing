package com.ameltaleb.pricing.domain.ports.output;

import java.time.LocalDateTime;
import java.util.List;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;

public interface PriceOutputPort {
     List<Price> findPricesByProductAndBrand(BrandId brandId, ProductId productId);

     List<Price> findByBrandIdAndProductIdAndDate(BrandId brandId, ProductId productId, LocalDateTime applicationDate);
}
