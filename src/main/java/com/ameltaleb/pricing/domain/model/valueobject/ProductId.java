package com.ameltaleb.pricing.domain.model.valueobject;

public record ProductId(Long value) {

        public ProductId{
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
    }
    }
}
