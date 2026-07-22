package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CategoryRepository extends ReactiveCrudRepository<Category, UUID> {

    Category getCategoryByName(String string);

}
