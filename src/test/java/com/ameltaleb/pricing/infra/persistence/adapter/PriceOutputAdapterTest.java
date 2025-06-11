package com.ameltaleb.pricing.infra.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.ameltaleb.pricing.domain.exception.PriceNotFoundException;
import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.LocalCurrency;
import com.ameltaleb.pricing.domain.model.valueobject.PriceRange;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.domain.ports.output.CurrencyValidationPort;
import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;
import com.ameltaleb.pricing.infra.persistence.mapper.PriceEntityMapper;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
@ExtendWith(MockitoExtension.class)
class PriceOutputAdapterTest {

    @Mock
    private JpaPriceRepository jpaRepository;

    @Mock
    private PriceEntityMapper mapper;

    @InjectMocks
    private PriceOutputAdapter adapter;

    private final LocalDateTime NOW = LocalDateTime.parse("2020-06-14T00:00:00");
    private final LocalDateTime FUTURE =LocalDateTime.parse("2020-12-31T23:59:59");
    private final BrandId BRAND_ID = new BrandId(1);
    private final ProductId PRODUCT_ID = new ProductId(35455);
    private final String FIRST_DATE= "2020-06-15 23:59:59";
    @Mock
    private CurrencyValidationPort mockValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new PriceOutputAdapter(jpaRepository, mapper); 
    }

    @Disabled
    @Test
    void testFindByBrandIdAndProductIdAndDate_returnsPrices() {

        PriceEntity entity = new PriceEntity(); 
        // set entity fields as needed for mapper conversion
        when(jpaRepository.findByBrandIdAndProductIdAndDate(BRAND_ID.value(), PRODUCT_ID.value(), FIRST_DATE))
            .thenReturn(Arrays.asList(entity));

        // When
        List<Price> prices = adapter.findByBrandIdAndProductIdAndDate(BRAND_ID, PRODUCT_ID, FIRST_DATE);

        // Then
        assertNotNull(prices);
        assertFalse(prices.isEmpty());
        assertEquals(1, prices.size());
        verify(jpaRepository).findByBrandIdAndProductIdAndDate(1, 35455, FIRST_DATE);
        verify(mapper).toDomain(entity);
    }

  @Test
    void testFindByBrandIdAndProductIdAndDate_throwsExceptionWhenEmpty() {
        String date = "2023-06-10";

        when(jpaRepository.findByBrandIdAndProductIdAndDate(BRAND_ID.value(), PRODUCT_ID.value(), date))
            .thenReturn(List.of());

        // When + Then
        assertThrows(PriceNotFoundException.class, () -> {
            adapter.findByBrandIdAndProductIdAndDate(BRAND_ID, PRODUCT_ID, date);
        });
    }


    @Test
    void findPricesByProductAndBrand_emptyResult_returnsEmptyList() {
        when(jpaRepository.findPricesByProductAndBrand(2, 35455))
            .thenReturn(List.of());

        List<Price> result = adapter.findPricesByProductAndBrand(new BrandId(2), PRODUCT_ID);

        assertTrue(result.isEmpty());
    }

    @Disabled
    @Test
    void testFindPricesByProductAndBrand_returnsPrices() {
        when(mockValidator.isValid("EUR")).thenReturn(true);

        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1);
        entity.setProductId(35455);
        entity.setPriceList(1);

        Price domainPrice = new Price(
            new BrandId(1),
            new ProductId(35455),
            new PriceRange(NOW, FUTURE),
            1, 0,
            new BigDecimal("35.50"),
            new LocalCurrency("EUR", mockValidator)
        );

        when(jpaRepository.findPricesByProductAndBrand(1, 35455))
            .thenReturn(Arrays.asList(entity));
        when(mapper.toDomain(entity)).thenReturn(domainPrice);

        List<Price> prices = adapter.findPricesByProductAndBrand(new BrandId(1), new ProductId(35455));

        assertNotNull(prices);
        assertFalse(prices.isEmpty());
        assertEquals(1, prices.size());
        assertEquals(1, prices.get(0).priceList());
    }
}
