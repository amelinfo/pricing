package com.ameltaleb.pricing.infra.persistence.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;

@Component
public class PriceEntityMapper {
    private final CurrencyValidationPort currencyValidator;

    public PriceEntityMapper(CurrencyValidationPort currencyValidator) {
        this.currencyValidator = currencyValidator;
    }

    public Price toDomain(PriceEntity entity) {
        Objects.requireNonNull(entity, "PriceEntity cannot be null");
        
        return new Price(
            new BrandId(entity.getBrandId()),
            new ProductId(entity.getProductId()),
            new PriceRange(entity.getStartDate(), entity.getEndDate()),
            entity.getPriceList(),
            entity.getPriority(),
            entity.getPrice(),
            new LocalCurrency(entity.getCurrency(), currencyValidator)
        );
    }

    public List<Price> toDomainList(List<PriceEntity> entities) {
        Objects.requireNonNull(entities, "PriceEntity list cannot be null");
        
        return entities.stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    public PriceEntity toEntity(Price domain) {
        Objects.requireNonNull(domain, "Price domain object cannot be null");
        
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(domain.brandId().value());
        entity.setProductId(domain.productId().value());
        entity.setStartDate(domain.applicableRange().startDate());
        entity.setEndDate(domain.applicableRange().endDate());
        entity.setPriceList(domain.priceList());
        entity.setPriority(domain.priority());
        entity.setPrice(domain.amount());
        entity.setCurrency(domain.currency().currencyCode());
        return entity;
    }
}