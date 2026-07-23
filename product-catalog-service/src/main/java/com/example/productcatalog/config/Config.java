package com.example.productcatalog.config;

import com.example.productcatalog.repository.CategoryRepository;
import com.example.productcatalog.service.CategoryService;
import com.example.productcatalog.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Config {
    private final CategoryRepository repository;
    private final ProductService productService;

    public Config(CategoryRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    @Bean
    public CategoryService categoryService() {
        log.info("");
        return new CategoryService(repository, productService);
    }
}
