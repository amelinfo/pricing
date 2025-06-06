package com.ameltaleb.pricing.domain.model.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class PriceRangeTest {

    private final LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
    private final LocalDateTime end = LocalDateTime.of(2020, 6, 14, 23, 59);

    @Test
    void shouldCreateValidPriceRange() {
        PriceRange range = new PriceRange(start, end);
        assertEquals(start, range.startDate());
        assertEquals(end, range.endDate());
    }

    @Test
    void shouldThrowExceptionIfStartDateIsAfterEndDate() {
        LocalDateTime invalidEnd = start.minusDays(1);
        assertThrows(IllegalArgumentException.class, () -> new PriceRange(start, invalidEnd));
    }

    @Test
    void shouldThrowExceptionIfStartOrEndIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new PriceRange(null, end));
        assertThrows(IllegalArgumentException.class, () -> new PriceRange(start, null));
    }

    @Test
    void shouldContainDateInsideRange() {
        PriceRange range = new PriceRange(start, end);
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 12, 0);
        assertTrue(range.contains(date));
    }

    @Test
    void shouldNotContainDateOutsideRange() {
        PriceRange range = new PriceRange(start, end);
        LocalDateTime before = start.minusMinutes(1);
        LocalDateTime after = end.plusMinutes(1);
        assertFalse(range.contains(before));
        assertFalse(range.contains(after));
    }
}