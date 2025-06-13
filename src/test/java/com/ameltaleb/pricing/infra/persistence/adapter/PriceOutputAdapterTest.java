package com.ameltaleb.pricing.infra.persistence.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ameltaleb.pricing.domain.exception.PriceNotFoundException;
import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;
import com.ameltaleb.pricing.infra.persistence.mapper.PriceEntityMapper;

@ExtendWith(MockitoExtension.class)
class PriceOutputAdapterTest {

    @Mock
    private JpaPriceRepository jpaRepository;

    @Mock
    private PriceEntityMapper mapper;

    @InjectMocks
    private PriceOutputAdapter adapter;

    private final BrandId brandId = new BrandId(1);
    private final ProductId productId = new ProductId(35455);
    private final LocalDateTime date = LocalDateTime.now();

    @Test
    void findByBrandIdAndProductIdAndDate_ReturnsPrices() {
        // Arrange
        PriceEntity entity = new PriceEntity();
        Price price = mock(Price.class);
        when(jpaRepository.findByBrandIdAndProductIdAndDate(1, 35455, date))
            .thenReturn(List.of(entity));
        when(mapper.toDomainList(List.of(entity))).thenReturn(List.of(price));

        // Act
        List<Price> result = adapter.findByBrandIdAndProductIdAndDate(brandId, productId, date);

        // Assert
        assertEquals(1, result.size());
        verify(jpaRepository).findByBrandIdAndProductIdAndDate(1, 35455, date);
    }

    @Test
    void findByBrandIdAndProductIdAndDate_ThrowsWhenNotFound() {
        // Arrange
        when(jpaRepository.findByBrandIdAndProductIdAndDate(1, 35455, date))
            .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(PriceNotFoundException.class, () -> 
            adapter.findByBrandIdAndProductIdAndDate(brandId, productId, date));
    }

    @Test
    void findPricesByProductAndBrand_ReturnsPrices() {
        // Arrange
        PriceEntity entity = new PriceEntity();
        Price price = mock(Price.class);
        when(jpaRepository.findPricesByProductAndBrand(1, 35455))
            .thenReturn(List.of(entity));
        when(mapper.toDomainList(List.of(entity))).thenReturn(List.of(price));

        // Act
        List<Price> result = adapter.findPricesByProductAndBrand(brandId, productId);

        // Assert
        assertEquals(1, result.size());
        verify(jpaRepository).findPricesByProductAndBrand(1, 35455);
    }
}