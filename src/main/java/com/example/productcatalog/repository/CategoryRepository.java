package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Category;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CategoryRepository extends ReactiveCrudRepository<Category, UUID> {
    @Query("SELECT * FROM category WHERE name = :name")
    Mono<Category> getCategoryByName(@Param("name") String name);
}
