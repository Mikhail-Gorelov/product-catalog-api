package com.example.productcatalog.service;

import com.example.productcatalog.api.CategoryDto;
import com.example.productcatalog.api.ReactorCategoryServiceGrpc;
import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.repository.CategoryRepository;
import io.grpc.BindableService;
import io.grpc.ServerServiceDefinition;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@CustomAnnotation
public class CategoryService extends ReactorCategoryServiceGrpc.CategoryServiceImplBase implements BindableService {

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

    @Override
    public Mono<CategoryDto> createCategory(CategoryDto category) {
        return categoryRepository.save(category);
    }

    @Override
    public Mono<com.google.protobuf.Empty> deleteCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getRequestId().getClientId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Product> products = productService.findAllByIdAndCategory(category);
        for (Product product : products) {
            product.setActive(true);
            product.setCategory(null);
            productService.createProduct(product);
        }
        categoryRepository.deleteById(categoryId);
        return null;
    }

    @Override
    public Flux<CategoryDto> getAllCategories(reactor.core.publisher.Mono<com.google.protobuf.Empty> request) {
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
