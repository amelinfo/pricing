package com.ameltaleb.pricing;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Ensure test profile is active
class PricingApplicationTests {

	@Autowired
    private ApplicationContext context;
    
    @Test
    void contextLoads() {
        assertNotNull(context);// just to verify context loads
    }
}
