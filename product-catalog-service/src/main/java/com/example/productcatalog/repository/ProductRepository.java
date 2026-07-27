package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Product;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<Product,UUID> {

    @Query("SELECT * FROM Product WHERE (:categoryId::uuid IS NULL OR category_id = :categoryId::uuid) " +
            "AND (:name IS NULL OR name ILIKE '%' || COALESCE(:name, '') || '%') " +
            "AND (:minPrice IS NULL OR price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR price <= :maxPrice) ")
    Flux<Product> findByFilters(
            @Param("categoryId") UUID categoryId,
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    Flux<Product> findAllByCategoryId(UUID categoryId);
}
