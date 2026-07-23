package com.example.productcatalog.controller;

import com.example.productcatalog.entity.Product;
import com.example.productcatalog.service.CategoryService;
import com.example.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class  ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return productService.createProduct(product)
                .map(p -> new ResponseEntity<>(p, HttpStatus.CREATED));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id)
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable UUID id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK));
    }

    @GetMapping
    public Flux<Product> searchProducts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return productService.searchProducts(categoryId, name, minPrice, maxPrice);
    }
}
