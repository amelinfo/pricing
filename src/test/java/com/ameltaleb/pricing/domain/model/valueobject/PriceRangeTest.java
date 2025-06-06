package com.ameltaleb.pricing.domain.model.valueobject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class PriceRangeTest {

        @Test
    void rejectsEndDateBeforeStartDate() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
            () -> new PriceRange(now, now.minusDays(1)));
    }

    @Test
    void checksDateContainment() {
        PriceRange range = new PriceRange(
            LocalDateTime.parse("2024-05-01T00:00:00"),
            LocalDateTime.parse("2024-05-31T23:59:59")
        );
        assertTrue(range.contains(LocalDateTime.parse("2024-05-15T12:00:00")));
    }
}
