package com.ameltaleb.pricing.domain.model.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;

public final class PriceRange {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private PriceRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PriceRange of(LocalDateTime startDate, LocalDateTime endDate) {
        return new PriceRange(startDate, endDate);
    }

    public boolean contains(LocalDateTime date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceRange that = (PriceRange) o;
        return startDate.equals(that.startDate) && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return "PriceRange{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}