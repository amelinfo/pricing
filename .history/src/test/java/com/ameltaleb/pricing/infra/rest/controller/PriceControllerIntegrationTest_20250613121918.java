package com.ameltaleb.pricing.infra.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import com.ameltaleb.pricing.application.dto.PriceResponse;

@SpringBootTest
@ActiveProfiles("test")
public class PriceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final Long PRODUCT_ID = 35455L;
    private static final Long BRAND_ID = 1L;
    private static final String BASE_URL = "http://localhost:";

    private String getUrl(LocalDateTime date) {
        return BASE_URL + port + "/api/prices?date=" + date.format(FORMATTER) + 
               "&productId=" + PRODUCT_ID + "&brandId=" + BRAND_ID;
    }

    @Test
    public void test1_10amOn14th() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(
            getUrl(dateTime), PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        PriceResponse priceResponse = response.getBody();
        assertNotNull(priceResponse);
        assertEquals(1, priceResponse.brandId());
        assertEquals(35455, priceResponse.productId());
        assertEquals(0, new BigDecimal("35.50").compareTo(priceResponse.price()));
    }

    @Test
    public void test2_4pmOn14th() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(
            getUrl(dateTime), PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        PriceResponse priceResponse = response.getBody();
        assertNotNull(priceResponse);
        assertEquals(1, priceResponse.brandId());
        assertEquals(35455, priceResponse.productId());
        assertEquals(0, new BigDecimal("25.45").compareTo(priceResponse.price()));
    }

    @Test
    public void test3_9pmOn14th() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 14, 21, 0, 0);
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(
            getUrl(dateTime), PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        PriceResponse priceResponse = response.getBody();
        assertNotNull(priceResponse);
        assertEquals(1, priceResponse.brandId());
        assertEquals(35455, priceResponse.productId());
        assertEquals(0, new BigDecimal("35.50").compareTo(priceResponse.price()));
    }

    @Test
    public void test4_10amOn15th() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 15, 10, 0, 0);
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(
            getUrl(dateTime), PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        PriceResponse priceResponse = response.getBody();
        assertNotNull(priceResponse);
        assertEquals(1, priceResponse.brandId());
        assertEquals(35455, priceResponse.productId());
        assertEquals(0, new BigDecimal("30.50").compareTo(priceResponse.price()));
    }

    @Test
    public void test5_9pmOn16th() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 16, 21, 0, 0);
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(
            getUrl(dateTime), PriceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        PriceResponse priceResponse = response.getBody();
        assertNotNull(priceResponse);
        assertEquals(1, priceResponse.brandId());
        assertEquals(35455, priceResponse.productId());
        assertEquals(0, new BigDecimal("38.95").compareTo(priceResponse.price()));
    }

    @AfterEach
    public void cleanup() {

    }
}