package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CategoryRepository extends R2dbcRepository<Category, UUID> {

    Mono<Category> getCategoryByName(String string);
}
