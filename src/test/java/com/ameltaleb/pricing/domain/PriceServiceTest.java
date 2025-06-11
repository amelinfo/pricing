package com.ameltaleb.pricing.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;

class PriceServiceTest {
    private final LocalDateTime NOW = LocalDateTime.now();
    private final PriceOutputPort mockPort = mock(PriceOutputPort.class);
    private final PriceService service = new PriceService(mockPort);
    
    @Mock
    private CurrencyValidationPort mockValidator;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockValidator.isValid("EUR")).thenReturn(true);
    }
    @Test
    void validateNoOverlappingPrices_whenOverlapExists_throws() {
        // Setup overlapping prices
        Price existing = new Price(
            new BrandId(1), new ProductId(35455),
            new PriceRange(NOW, NOW.plusDays(1)),
            1, 0, new BigDecimal("35.50"), new LocalCurrency("EUR", mockValidator)
        );

        Price newPrice = new Price(
            new BrandId(1), new ProductId(35455),
            new PriceRange(NOW.plusHours(12), NOW.plusDays(2)),
            2, 0, new BigDecimal("30.00"), new LocalCurrency("EUR", mockValidator)
        );

        when(mockPort.findPricesByProductAndBrand(any(), any()))
            .thenReturn(List.of(existing));

        Exception ex = assertThrows(IllegalStateException.class,
            () -> service.validateNoOverlappingPrices(newPrice));

        assertTrue(ex.getMessage().contains("conflicts with existing price"));
    }

/*         @Test
    void testScenario1_HighestPriorityAt10AM() {
        String date = "2020-06-14 10:00";
        when(mockPort.findByBrandIdAndProductIdAndDate(any(), any(), eq(date)))
            .thenReturn(List.of(
                testPrice(0, 35.50),
                testPrice( 1, 25.45)
            ));

        Price result = service.findApplicablePrice(date, 1, 35455);
        assertEquals(35.50, result.amount());
    }

        private Price testPrice(int priceList, double price) {
        return new Price(new BrandId(1), new ProductId(35455), new PriceRange(LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59")), priceList, priceList, null, null);
    } */
}
