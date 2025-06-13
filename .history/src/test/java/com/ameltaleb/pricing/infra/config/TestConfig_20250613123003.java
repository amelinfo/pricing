package com.ameltaleb.pricing.infra.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {
    
    @Bean
    public CurrencyValidationPort currencyValidator() {
        return mock(CurrencyValidationPort.class);
    }
    
    @Bean
    public PriceInputPort priceInputPort() {
        return mock(PriceInputPort.class);
    }
    
    @Bean
    public JpaPriceRepository jpaPriceRepository() {
        return mock(JpaPriceRepository.class);
    }
}