package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Product;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductRepository extends R2dbcRepository<Product,UUID> {

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) ")
    Flux<Product> findByFilters(
            @Param("categoryId") UUID categoryId,
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    Flux<Product> findAllByCategoryId(UUID categoryId);
}
