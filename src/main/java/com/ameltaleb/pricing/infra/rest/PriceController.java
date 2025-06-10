package com.ameltaleb.pricing.infra.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ameltaleb.pricing.application.dto.PriceResponse;
import com.ameltaleb.pricing.application.mapper.PriceMapper;
import com.ameltaleb.pricing.domain.exception.PriceNotFoundException;
import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceInputPort priceInputPort = null;

    @GetMapping
    public PriceResponse getApplicablePrice(
        @RequestParam String applicationDate,
        @RequestParam Integer productId,
        @RequestParam Integer brandId
    ) {
        Price price = priceInputPort.findApplicablePrice(
            applicationDate,
            new ProductId(productId),
            new BrandId(brandId)
        ).orElseThrow(() -> new PriceNotFoundException("Price not found"));
        
        return PriceMapper.toResponse(price);
    }
}