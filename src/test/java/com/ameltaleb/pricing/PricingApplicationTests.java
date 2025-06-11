package com.ameltaleb.pricing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Ensure test profile is active
class PricingApplicationTests {

    @Test
    void contextLoads() {
        // Empty test just to verify context loads
    }
}
