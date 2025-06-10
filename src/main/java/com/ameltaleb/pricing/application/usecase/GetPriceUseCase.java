package com.ameltaleb.pricing.application.usecase;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;
import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;

public class GetPriceUseCase implements PriceInputPort {
    
    private final PriceOutputPort priceOutputPort;
    
    public GetPriceUseCase(PriceOutputPort priceOutputPort) {
        this.priceOutputPort = priceOutputPort;
    }
    
    @Override
    public Optional<Price> findApplicablePrice(
        String applicationDate,
        ProductId productId,
        BrandId brandId
    ) {
        // 1. Fetch all applicable prices
        List<Price> prices = priceOutputPort.findByBrandIdAndProductIdAndDate(
            brandId,
            productId,
            applicationDate
        );
        
        // 2. Apply business rule: Select highest priority price
        return prices.stream()
            .max(Comparator.comparing(Price::priority));
    }
}