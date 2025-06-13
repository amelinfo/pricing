package com.ameltaleb.pricing.infra.persistence.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ameltaleb.pricing.infra.persistence.entity.PriceEntity;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
        SELECT p FROM PriceEntity p
        WHERE p.brandId = :brandId
        AND p.productId = :productId
        AND :date BETWEEN p.startDate AND p.endDate
        ORDER BY p.priority DESC
        """)
    List<PriceEntity> findByBrandIdAndProductIdAndDate(
        @Param("brandId") Integer brandId,
        @Param("productId") Integer productId,
        @Param("date") LocalDateTime date
    );

        @Query("""
        SELECT p FROM PriceEntity p
        WHERE p.brandId = :brandId
        AND p.productId = :productId
        ORDER BY p.startDate ASC
        """)
    List<PriceEntity> findPricesByProductAndBrand(
        @Param("brandId") Integer brandId,
        @Param("productId") Integer productId
    );
}
