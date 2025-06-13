package com.ameltaleb.pricing.application.usecase;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;
import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;

@Service
public class GetPriceUseCase implements PriceInputPort {
    
    private final PriceOutputPort priceOutputPort;
    
    public GetPriceUseCase(PriceOutputPort priceOutputPort) {
        this.priceOutputPort = priceOutputPort;
    }
    
    @Override
    public Optional<Price> findApplicablePrice(
        LocalDateTime applicationDate,
        ProductId productId,
        BrandId brandId
    ) {
        return priceOutputPort.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate)
            .stream()
            .max(Comparator.comparing(Price::priority));
    }
}