package com.ameltaleb.pricing.application.mapper;

import com.ameltaleb.pricing.application.dto.PriceResponse;
import com.ameltaleb.pricing.domain.model.aggregate.Price;

public class PriceMapper {

    public static PriceResponse toResponse(Price price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }

        return new PriceResponse(
            price.productId().value(),
            price.brandId().value(),
            price.priceList(),
            price.applicableRange().startDate(),
            price.applicableRange().endDate(),
            price.amount(),
            price.currency().currencyCode()
        );
    }
}
