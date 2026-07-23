package com.example.productcatalog.service;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@CustomAnnotation
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    public final AtomicInteger postProxyInvokeCount = new AtomicInteger(0);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductService productService) {
        log.info("");
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        log.info("init");
    }

    @PreDestroy
    public void destroy() {
        log.info("destroy");
    }

    public Mono<Category> createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Mono<Void> deleteCategory(UUID categoryId) {
        return categoryRepository.deleteById(categoryId);
    }

    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        postProxyInvokeCount.incrementAndGet();
    }

    public int getPostProxyInvokeCount() {
        return postProxyInvokeCount.get();
    }


    public Category updateCategory(UUID categoryId, Category updatedCategory) {
        return categoryRepository.findById(categoryId)
                .flatMap(category -> {
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());
                    return categoryRepository.save(category);
                }).block();
    }

    public Mono<Category> getCategoryName(String name) {
        return categoryRepository.getCategoryByName(name);
    }
}
