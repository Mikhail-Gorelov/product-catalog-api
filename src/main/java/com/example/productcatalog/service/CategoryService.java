package com.example.productcatalog.service;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
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

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Product> products = productService.findAllByIdAndCategory(category);
        for (Product product : products) {
            product.setActive(true);
            product.setCategory(null);
            productService.createProduct(product);
        }
        categoryRepository.deleteById(categoryId);
    }

    public List<Category> getAllCategories() {
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
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());
        return categoryRepository.save(category);
    }

    public Category getCategoryName(String name) {
        return categoryRepository.getCategoryByName(name);
    }
}
