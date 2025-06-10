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
    void overlaps_whenRangesOverlap_returnsTrue() {
        PriceRange range1 = new PriceRange(start, end);
        PriceRange range2 = new PriceRange(start.minusHours(1), start.plusHours(1));
        assertTrue(range1.overlaps(range2));
    }

    @Test
    void overlaps_whenRangesDontOverlap_returnsFalse() {
        PriceRange range1 = new PriceRange(start, start.plusHours(2));
        PriceRange range2 = new PriceRange(start.plusHours(3), start.plusHours(4));
        assertFalse(range1.overlaps(range2));
    }

    @Test
    void overlaps_throwsOnNullInput() {
        PriceRange range = new PriceRange(start, end);
        assertThrows(NullPointerException.class, () -> range.overlaps(null));
    }
}