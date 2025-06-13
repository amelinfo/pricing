package com.ameltaleb.pricing.infra.config;

import com.ameltaleb.pricing.domain.ports.output.PriceOutputPort;
import com.ameltaleb.pricing.infra.persistence.adapter.PriceOutputAdapter;
import com.ameltaleb.pricing.infra.persistence.mapper.PriceEntityMapper;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PricingConfiguration {

    @Bean
    PriceOutputPort priceOutputPort(
        JpaPriceRepository jpaRepository,
        PriceEntityMapper priceEntityMapper
    ) {
        return new PriceOutputAdapter(jpaRepository, priceEntityMapper);
    }
}