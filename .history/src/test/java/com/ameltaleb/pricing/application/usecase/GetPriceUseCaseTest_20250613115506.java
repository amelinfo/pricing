package com.ameltaleb.pricing.application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.ameltaleb.pricing.domain.exception.PriceNotFoundException;
import com.ameltaleb.pricing.domain.model.aggregate.Price;
import com.ameltaleb.pricing.domain.model.valueobject.BrandId;
import com.ameltaleb.pricing.domain.model.valueobject.ProductId;
import com.ameltaleb.pricing.infra.persistence.adapter.PriceOutputAdapter;
import com.ameltaleb.pricing.infra.persistence.jpa.JpaPriceRepository;
import com.ameltaleb.pricing.infra.persistence.mapper.PriceEntityMapper;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"/schema.sql", "/data.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetPriceUseCaseTest {

    @Autowired
    private JpaPriceRepository priceRepository;

    @Autowired
    private PriceEntityMapper mapper;

    private GetPriceUseCase useCase;

    @Test
    void findApplicablePrice_At10AMOn14th_ReturnsCorrectPrice() {
        // Arrange
        PriceOutputAdapter adapter = new PriceOutputAdapter(priceRepository, mapper);
        useCase = new GetPriceUseCase(adapter);
        
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        ProductId productId = new ProductId(35455);
        BrandId brandId = new BrandId(1);

        // Act
        Optional<Price> result = useCase.findApplicablePrice(date, productId, brandId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(0,result.get().amount().compareTo(new BigDecimal(35.50)));
        assertEquals(1, result.get().priceList());
    }

    @Test
    void findApplicablePrice_At4PMOn14th_ReturnsCorrectPrice() {
        // Arrange
        PriceOutputAdapter adapter = new PriceOutputAdapter(priceRepository, mapper);
        useCase = new GetPriceUseCase(adapter);
        
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        ProductId productId = new ProductId(35455);
        BrandId brandId = new BrandId(1);

        // Act
        Optional<Price> result = useCase.findApplicablePrice(date, productId, brandId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(0,result.get().amount().compareTo(new BigDecimal(25.45)));
        assertEquals(2, result.get().priceList());
    }

    @Test
    void findApplicablePrice_At9PMOn14th_ReturnsCorrectPrice() {
        // Arrange
        PriceOutputAdapter adapter = new PriceOutputAdapter(priceRepository, mapper);
        useCase = new GetPriceUseCase(adapter);
        
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        ProductId productId = new ProductId(35455);
        BrandId brandId = new BrandId(1);

        // Act
        Optional<Price> result = useCase.findApplicablePrice(date, productId, brandId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(0,result.get().amount().compareTo(new BigDecimal(35.50)));
        assertEquals(1, result.get().priceList());
    }

    @Test
    void findApplicablePrice_At10AMOn15th_ReturnsCorrectPrice() {
        // Arrange
        PriceOutputAdapter adapter = new PriceOutputAdapter(priceRepository, mapper);
        useCase = new GetPriceUseCase(adapter);
        
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
        ProductId productId = new ProductId(35455);
        BrandId brandId = new BrandId(1);

        // Act
        Optional<Price> result = useCase.findApplicablePrice(date, productId, brandId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(0,result.get().amount().compareTo(new BigDecimal(35.50)));
        assertEquals(3, result.get().priceList());
    }

    @Test
    void findApplicablePrice_At9PMOn16th_ReturnsCorrectPrice() {
        // Arrange
        PriceOutputAdapter adapter = new PriceOutputAdapter(priceRepository, mapper);
        useCase = new GetPriceUseCase(adapter);
        
        LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0);
        ProductId productId = new ProductId(35455);
        BrandId brandId = new BrandId(1);

        // Act
        Optional<Price> result = useCase.findApplicablePrice(date, productId, brandId);
        BigDecimal actual = result.get().amount();
        BigDecimal expected = new BigDecimal("38.95");
        // Assert
        assertTrue(result.isPresent());
        assertTrue(actual.stripTrailingZeros().compareTo(
                    expected.stripTrailingZeros()) == 0);
        assertEquals(4, result.get().priceList());
    }

    @Test
    void findApplicablePrice_WithNonExistingProduct_ReturnsEmpty() {
        // Arrange
        PriceOutputAdapter adapter = new PriceOutputAdapter(priceRepository, mapper);
        useCase = new GetPriceUseCase(adapter);
        
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        ProductId productId = new ProductId(99999); // Non-existing product
        BrandId brandId = new BrandId(1);

        // Assert
        assertThrows(PriceNotFoundException.class, () -> {
        useCase.findApplicablePrice(date, productId, brandId);
    });
    }
}