package com.ameltaleb.pricing.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;
import com.ameltaleb.pricing.infra.validation.CurrencyValidatorAdapter;

class GetPriceUseCaseTest {
    private final PriceOutputPort mockOutputPort = mock(PriceOutputPort.class);
    private final GetPriceUseCase useCase = new GetPriceUseCase(mockOutputPort);
    
    @Test
    void whenMultiplePricesExist_selectsHighestPriority() {
        // Arrange
        String testDate = "2020-06-15 00:00:00";
        when(mockOutputPort.findByBrandIdAndProductIdAndDate(any(), any(), any()))
            .thenReturn(List.of(
                new Price(
            new BrandId(1),
            new ProductId(35455),
            new PriceRange(
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
            ),
            1,
            1,
            new BigDecimal("35.50"),
            new LocalCurrency("EUR", new CurrencyValidatorAdapter())
        ),
                        new Price(
            new BrandId(1),
            new ProductId(35455),
            new PriceRange(
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
            ),
            1,
            2,
            new BigDecimal("35.50"),
            new LocalCurrency("EUR", new CurrencyValidatorAdapter())
        )));
        
        // Act
        Optional<Price> result = useCase.findApplicablePrice(
            testDate, 
            new ProductId(35455), 
            new BrandId(1)
        );
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(new BigDecimal(35.50).stripTrailingZeros(), result.get().amount().stripTrailingZeros());
    }
}
