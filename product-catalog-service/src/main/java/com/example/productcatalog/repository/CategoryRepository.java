package com.example.productcatalog.repository;

import com.example.productcatalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Category getCategoryByName(String string);
}
