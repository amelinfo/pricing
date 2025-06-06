package com.ameltaleb.pricing.domain.model.valueobject;

import java.time.LocalDateTime;

public record PriceRange(LocalDateTime startDate, LocalDateTime endDate) {
    public PriceRange {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
    }

    // Domain behavior
    public boolean contains(LocalDateTime date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}