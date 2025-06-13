package com.ameltaleb.pricing.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
    Integer productId,
    Integer brandId,
    Integer priceList,
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startDate,
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endDate,
    
    BigDecimal price,
    String currency
) {
    // Static factory method (optional)
    public static PriceResponse fromDomain(
        Integer productId,
        Integer brandId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
    ) {
        return new PriceResponse(
            productId,
            brandId,
            priceList,
            startDate,
            endDate,
            price,
            currency
        );
    }
}