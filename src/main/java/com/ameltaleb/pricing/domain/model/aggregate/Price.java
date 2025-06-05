package com.ameltaleb.pricing.domain.model.aggregate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;

public class Price {
    private BrandId brandId;
    private ProductId productId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Integer priority;
    private BigDecimal price;
    private LocalCurrency currency;
    
    public Price(BrandId brandId2, ProductId productId2, LocalDateTime startDate2, LocalDateTime endDate2,
            Integer priceList2, Integer priority2, BigDecimal price2, LocalCurrency currency2) {
        //TODO Auto-generated constructor stub
    }

    // Factory method
    public static Price create(BrandId brandId, ProductId productId, 
                             LocalDateTime startDate, LocalDateTime endDate,
                             Integer priceList, Integer priority,
                             BigDecimal price, LocalCurrency currency) {
        validatePriceDates(startDate, endDate);
        return new Price(brandId, productId, startDate, endDate, 
                       priceList, priority, price, currency);
    }
    
    private static void validatePriceDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    // Domain logic
    public boolean isApplicable(LocalDateTime applicationDate) {
        return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
    }
}