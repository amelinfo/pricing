package com.ameltaleb.pricing.infra.persistence.adapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ameltaleb.pricing.domain.exception.PriceNotFoundException;
import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;
import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;
import com.ameltaleb.pricing.infra.persistence.mapper.PriceEntityMapper;

@Repository
public class PriceOutputAdapter implements PriceOutputPort {
    private final JpaPriceRepository jpaRepository;
    private final PriceEntityMapper mapper;

    public PriceOutputAdapter(JpaPriceRepository jpaRepository, PriceEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Price> findByBrandIdAndProductIdAndDate(
        BrandId brandId, 
        ProductId productId, 
        LocalDateTime date
    ) {
        List<PriceEntity> entities = jpaRepository.findByBrandIdAndProductIdAndDate(
            brandId.value(),
            productId.value(),
            date
        );
        
        if (entities.isEmpty()) {
            throw new PriceNotFoundException(
                String.format("No price found for brandId: %d, productId: %d at date: %s",
                    brandId.value(),
                    productId.value(),
                    date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            );
        }
        
        return mapper.toDomainList(entities);
    }

    @Override
    public List<Price> findPricesByProductAndBrand(BrandId brandId, ProductId productId) {
        List<PriceEntity> entities = jpaRepository.findPricesByProductAndBrand(
            brandId.value(),
            productId.value()
        );
        
        if (entities.isEmpty()) {
            throw new PriceNotFoundException(
                String.format("No prices found for brandId: %d and productId: %d",
                    brandId.value(),
                    productId.value())
            );
        }
        
        return mapper.toDomainList(entities);
    }
}