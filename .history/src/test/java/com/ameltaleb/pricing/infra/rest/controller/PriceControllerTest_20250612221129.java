package com.ameltaleb.pricing.infra.rest.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.input.PriceInputPort;
import com.ameltaleb.pricing.infra.validation.CurrencyValidatorAdapter;

@WebMvcTest(PriceController.class)
@Import(PriceControllerTest.MockConfig.class)
class PriceControllerTest {

    @TestConfiguration
    static class MockConfig {
        @Bean
        public PriceInputPort priceInputPort() {
            return Mockito.mock(PriceInputPort.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceInputPort priceInputPort;

    @Test
    void testGetApplicablePrice_returnsValidResponse() throws Exception {
        // Scenario 1
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14 10:00:00");
        Integer brandId = 1;
        Integer productId = 35455;

        Price mockPrice = new Price(
            new BrandId(brandId),
            new ProductId(productId),
            new PriceRange(LocalDateTime.parse("2020-06-14T00:00:00"), LocalDateTime.parse("2020-12-31T23:59:59")),
            1,
            0,
            new BigDecimal("35.50"),
            new LocalCurrency("EUR", new CurrencyValidatorAdapter()) // mock validator
        );

        Mockito.when(priceInputPort.findApplicablePrice(applicationDate, new ProductId(productId), new BrandId(brandId)))
            .thenReturn(Optional.of(mockPrice));

        // Act + Assert
        mockMvc.perform(get("/prices")
                .param("applicationDate", applicationDate.toString())
                .param("productId", productId.toString())
                .param("brandId", brandId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(productId))
            .andExpect(jsonPath("$.brandId").value(brandId))
            .andExpect(jsonPath("$.priceList").value(1))
            .andExpect(jsonPath("$.price").value("35.5"));
    }

    @Test
    void testGetApplicablePrice_whenNotFound_throwsException() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-10 10:00:00");
        Integer productId = 35455;
        Integer brandId = 1;

        Mockito.when(priceInputPort.findApplicablePrice(applicationDate, new ProductId(productId), new BrandId(brandId)))
            .thenReturn(Optional.empty());

        mockMvc.perform(get("/prices")
                .param("applicationDate", applicationDate.toString())
                .param("productId", productId.toString())
                .param("brandId", brandId.toString()))
            .andExpect(status().isNotFound());
    }

    //Scenario2


}