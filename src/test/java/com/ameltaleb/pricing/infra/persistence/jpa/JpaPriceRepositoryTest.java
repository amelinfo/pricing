package com.ameltaleb.pricing.infra.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
class JpaPriceRepositoryTest {
    
    @Autowired
    private JpaPriceRepository repository;
    
    @Test
    void findPricesByProductAndBrand_returnsAllMatches() {
        List<PriceEntity> prices = repository.findPricesByProductAndBrand(1, 35455);
        
        assertEquals(4, prices.size()); // Matches sample data
        assertTrue(prices.get(0).getStartDate().isBefore(prices.get(1).getStartDate()));
    }
}