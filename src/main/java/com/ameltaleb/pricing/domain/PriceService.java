package com.ameltaleb.pricing.domain;

import java.util.List;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;

public class PriceService {
    private final PriceOutputPort priceOutputPort;

    public PriceService(PriceOutputPort priceOutputPort) {
        this.priceOutputPort = priceOutputPort;
    }

    public void validateNoOverlappingPrices(Price newPrice) {
        List<Price> existingPrices = priceOutputPort.findPricesByProductAndBrand(
            newPrice.brandId(),
            newPrice.productId()
        );

        existingPrices.stream()
            .filter(existing -> existing.priceList() != newPrice.priceList()) // Different price lists
            .filter(existing -> newPrice.applicableRange().overlaps(existing.applicableRange()))
            .findFirst()
            .ifPresent(conflicting -> {
                throw new IllegalStateException(
                    String.format("New price (list %d) conflicts with existing price (list %d) between %s and %s",
                        newPrice.priceList(),
                        conflicting.priceList(),
                        conflicting.applicableRange().startDate(),
                        conflicting.applicableRange().endDate())
                );
            });
    }
}