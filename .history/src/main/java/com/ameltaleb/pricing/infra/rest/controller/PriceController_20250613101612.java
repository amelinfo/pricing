package com.ameltaleb.pricing.infra.rest.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceInputPort priceInputPort;

    public PriceController(PriceInputPort priceInputPort) {
        this.priceInputPort = priceInputPort;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(
        @RequestParam LocalDateTime date,
        @RequestParam Integer productId,
        @RequestParam Integer brandId
    ) {
        return priceInputPort.findApplicablePrice(
            date,
            new ProductId(productId),
            new BrandId(brandId))
            .map(price -> ResponseEntity.ok(price))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}