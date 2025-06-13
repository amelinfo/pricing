package com.ameltaleb.pricing.infra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ameltaleb.pricing.infra.persistence.jpa")
@EntityScan(basePackages = "com.ameltaleb.pricing.infra.persistence.entity")
public class TestJpaConfig {
    // Spring Boot's auto-configuration will handle DataSource and EntityManagerFactory
    // No need for manual bean definitions with application-test.properties
}