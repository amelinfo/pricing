package com.ameltaleb.pricing.domain.model.valueobject;

public record BrandId(Integer value) {

    public BrandId{
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Brand ID must be positive");
    }
    }

    public Boolean isZaraBrand() {
        return value == 1; // 1 = ZARA
    }   
    
}