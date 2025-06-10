package com.ameltaleb.pricing.domain.model.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;

public record PriceRange(LocalDateTime startDate, LocalDateTime endDate) {
    public PriceRange {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
    }

    public boolean overlaps(PriceRange other) {
        Objects.requireNonNull(other, "Other price range cannot be null");
        return !this.startDate.isAfter(other.endDate()) && 
               !this.endDate.isBefore(other.startDate());
    }
}