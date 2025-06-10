package com.ameltaleb.pricing.infra.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;
import com.ameltaleb.pricing.infra.validation.CurrencyValidatorAdapter;

class PriceEntityMapperTest {
    private final PriceEntityMapper mapper = new PriceEntityMapper();

    // Test data builders
    private PriceEntity createValidEntity() {
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1);
        entity.setProductId(35455);
        entity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        entity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        entity.setPriceList(1);
        entity.setPriority(0);
        entity.setPrice(new BigDecimal("35.50"));
        entity.setCurrency("EUR");
        return entity;
    }

    private Price createValidDomain() {
        return new Price(
            new BrandId(1),
            new ProductId(35455),
            new PriceRange(
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59")
            ),
            1,
            0,
            new BigDecimal("35.50"),
            new LocalCurrency("EUR", new CurrencyValidatorAdapter())
        );
    }

    // --- toDomain() Tests ---
    @Test
    void toDomain_mapsAllFieldsCorrectly() {
        PriceEntity entity = createValidEntity();
        Price domain = mapper.toDomain(entity);

        assertEquals(entity.getBrandId(), domain.brandId().value());
        assertEquals(entity.getProductId(), domain.productId().value());
        assertEquals(entity.getStartDate(), domain.applicableRange().startDate());
        assertEquals(entity.getEndDate(), domain.applicableRange().endDate());
        assertEquals(entity.getPriceList(), domain.priceList());
        assertEquals(entity.getPriority(), domain.priority());
        assertEquals(entity.getPrice(), domain.amount());
        assertEquals(entity.getCurrency(), domain.currency().currencyCode());
    }

    @Test
    void toDomain_throwsOnNullEntity() {
        assertThrows(NullPointerException.class, () -> mapper.toDomain(null));
    }

    // --- toEntity() Tests ---
    @Test
    void toEntity_mapsAllFieldsCorrectly() {
        Price domain = createValidDomain();
        PriceEntity entity = mapper.toEntity(domain);

        assertEquals(domain.brandId().value(), entity.getBrandId());
        assertEquals(domain.productId().value(), entity.getProductId());
        assertEquals(domain.applicableRange().startDate(), entity.getStartDate());
        assertEquals(domain.applicableRange().endDate(), entity.getEndDate());
        assertEquals(domain.priceList(), entity.getPriceList());
        assertEquals(domain.priority(), entity.getPriority());
        assertEquals(domain.amount(), entity.getPrice());
        assertEquals(domain.currency().currencyCode(), entity.getCurrency());
    }

    @Test
    void toEntity_throwsOnNullDomain() {
        assertThrows(NullPointerException.class, () -> mapper.toEntity(null));
    }

    // --- Static Validator Test ---
    @Test
    void staticValidator_isInitialized() {
        assertNotNull(PriceEntityMapper.currencyValidator);
        assertTrue(PriceEntityMapper.currencyValidator.isValid("EUR"));
    }

    @Test
    void toDomain_invalidCurrency_throws() {
        PriceEntity entity = createValidEntity();
        entity.setCurrency("INVALID");
        assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(entity));
    }

    // domain list tests
        @Test
    void toDomainList_mapsAllEntities() {
        List<PriceEntity> entities = List.of(
            createTestPriceEntity(1, 35.50),
            createTestPriceEntity(2, 25.45)
        );

        List<Price> domains = mapper.toDomainList(entities);

        assertEquals(2, domains.size());
        assertEquals(35.50, domains.get(0).amount().doubleValue());
    }

    @Test
    void toDomainList_throwsOnNullInput() {
        assertThrows(NullPointerException.class, () -> mapper.toDomainList(null));
    }

    @Test
    void toDomainList_handlesEmptyList() {
        List<Price> result = mapper.toDomainList(List.of());
        assertTrue(result.isEmpty());
    }

    private PriceEntity createTestPriceEntity(int priceList, double price) {
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1);
        entity.setProductId(351);
        entity.setPriceList(priceList);
        entity.setPrice(BigDecimal.valueOf(price));
        entity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        entity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        entity.setPriority(0);
        entity.setCurrency("EUR");
        return entity;
    }
}