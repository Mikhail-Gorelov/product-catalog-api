package com.example.productcatalog.controller;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(@Qualifier("categoryService") CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Mono<ResponseEntity<Category>> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category)
                .map(createdCategory -> new ResponseEntity<>(createdCategory, HttpStatus.CREATED));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteCategory(@PathVariable UUID id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping
    public Flux<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Category>> updateCategory(@PathVariable UUID id, @RequestBody Category updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK));
    }
}