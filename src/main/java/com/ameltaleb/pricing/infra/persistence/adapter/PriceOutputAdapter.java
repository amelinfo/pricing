package com.ameltaleb.pricing.infra.persistence.adapter;

import java.util.List;

import com.ameltaleb.pricing.domain.exception.PriceNotFoundException;
import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;
import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;
import com.ameltaleb.pricing.infra.persistence.mapper.PriceEntityMapper;

public class PriceOutputAdapter implements PriceOutputPort {
    private JpaPriceRepository jpaRepository;
    private PriceEntityMapper mapper = new PriceEntityMapper();

    public PriceOutputAdapter(JpaPriceRepository jpaRepository, PriceEntityMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
    }
    
    @Override
    public List<Price> findByBrandIdAndProductIdAndDate(
        BrandId brandId, 
        ProductId productId, 
        String date
    ) {
        List<PriceEntity> entities = jpaRepository.findByBrandIdAndProductIdAndDate(
            brandId.value(),
            productId.value(),
            date
        );
        if (entities.isEmpty()) {
          throw new PriceNotFoundException();
        }
        return mapper.toDomainList(entities); // ← Conversion here
    }

    @Override
    public List<Price> findPricesByProductAndBrand( BrandId brandId, ProductId productId) {
        List<PriceEntity> entities = jpaRepository.findPricesByProductAndBrand(
            brandId.value(),
            productId.value()
        );
        return mapper.toDomainList(entities);
    }
}