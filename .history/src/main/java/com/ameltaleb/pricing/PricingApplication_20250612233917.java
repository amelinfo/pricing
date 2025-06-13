package com.ameltaleb.pricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.ameltaleb.pricing.domain",
    "com.ameltaleb.pricing.application", 
    "com.ameltaleb.pricing.infra"
})
@EnableJpaRepositories(basePackages = "com.ameltaleb.pricing.infra.persistence.jpa")  
@EntityScan(basePackages = "com.ameltaleb.pricing.infra.persistence.entity")  
public class PricingApplication {

/*     @Bean
    public PriceOutputPort priceOutputPort(JpaPriceRepository jpaRepository,PriceEntityMapper mapper) {
        return new PriceOutputAdapter(jpaRepository, mapper);
    } */
    public static void main(String[] args) {
        SpringApplication.run(PricingApplication.class, args);
    }
}
