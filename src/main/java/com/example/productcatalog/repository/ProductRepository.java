package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Product;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<Product,UUID> {

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:name IS NULL OR p.name ILIKE CONCAT('%', :name, '%')) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) ")
    List<Product> findByFilters(
            @Param("categoryId") UUID categoryId,
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    List<Product> findAllByCategoryId(UUID categoryId);
}
