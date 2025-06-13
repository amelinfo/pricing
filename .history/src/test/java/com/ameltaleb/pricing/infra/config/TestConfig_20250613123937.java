package com.ameltaleb.pricing.infra.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;

import static org.mockito.Mockito.mock;

@TestConfiguration
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = JpaPriceRepository.class))
public class TestConfig {
    
    @Bean
    @Primary  // Mark as primary to override the JPA version
    public JpaPriceRepository jpaPriceRepository() {
        return mock(JpaPriceRepository.class);
    }
    
    @Bean
    @Primary
    public PriceInputPort priceInputPort() {
        return mock(PriceInputPort.class);
    }
    
    @Bean
    public CurrencyValidationPort currencyValidator() {
        return mock(CurrencyValidationPort.class);
    }
}