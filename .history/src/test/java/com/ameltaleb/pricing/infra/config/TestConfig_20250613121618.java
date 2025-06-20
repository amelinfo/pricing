package com.ameltaleb.pricing.infra.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {
    
    @Bean
    public CurrencyValidationPort currencyValidator() {
        return mock(CurrencyValidationPort.class);
    }
}