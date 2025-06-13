package com.ameltaleb.pricing.infra.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;

@ExtendWith(MockitoExtension.class)
class PriceEntityMapperTest {

    private final CurrencyValidationPort validator = mock(CurrencyValidationPort.class);
    private final PriceEntityMapper mapper = new PriceEntityMapper(validator);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(validator.isValid("EUR")).thenReturn(true);
    }

    @Test
    void toDomain_ConvertsEntityCorrectly() {
        // Arrange
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1);
        entity.setProductId(35455);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusDays(1));
        entity.setPriceList(1);
        entity.setPriority(0);
        entity.setPrice(new BigDecimal(35.50));
        entity.setCurrency("EUR");

        // Act
        Price price = mapper.toDomain(entity);

        // Assert
        assertEquals(1, price.brandId().value());
        assertEquals(35455, price.productId().value());
        assertEquals(new BigDecimal(35.50), price.amount());
        verify(validator).isValid("EUR");
    }

    @Test
    void toDomain_ThrowsOnNullEntity() {
        assertThrows(NullPointerException.class, () -> mapper.toDomain(null));
    }

    @Test
    void toEntity_ConvertsDomainCorrectly() {
        // Arrange
        Price price = new Price(
            new BrandId(1),
            new ProductId(35455),
            new PriceRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1)),
            1,
            0,
            new BigDecimal(35.50),
            new LocalCurrency("EUR", validator)
        );

        // Act
        PriceEntity entity = mapper.toEntity(price);

        // Assert
        assertEquals(1, entity.getBrandId());
        assertEquals(35455, entity.getProductId());
        assertEquals(new BigDecimal(35.50), entity.getPrice());
        assertEquals("EUR", entity.getCurrency());
    }
}