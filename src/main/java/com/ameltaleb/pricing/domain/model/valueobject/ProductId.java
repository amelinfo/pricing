package com.ameltaleb.pricing.domain.model.valueobject;

public record ProductId(Integer value) {

    public ProductId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
    }

    public static ProductId of(Integer value) {
        return new ProductId(value);
    }

    @Override
    public String toString() {
        return "ProductId{" + value + "}";
    }
}
