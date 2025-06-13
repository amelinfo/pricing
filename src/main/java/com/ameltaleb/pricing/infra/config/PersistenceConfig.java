package com.ameltaleb.pricing.infra.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import com.ameltaleb.pricing.infra.validation.CurrencyValidatorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "com.ameltaleb.pricing.infra.persistence.repository")
@EntityScan(basePackages = "com.ameltaleb.pricing.infra.persistence.entity")
public class PersistenceConfig {
    
    @Bean
    public CurrencyValidationPort currencyValidator() {
        return new CurrencyValidatorAdapter();
    }
}