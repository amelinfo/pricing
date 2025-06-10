package com.ameltaleb.pricing.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException() {
        super("Price not found for given criteria");
    }

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}