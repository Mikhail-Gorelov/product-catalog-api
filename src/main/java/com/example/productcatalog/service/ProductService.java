package com.example.productcatalog.service;

import com.example.productcatalog.entity.Category;
import com.example.productcatalog.entity.Product;
import com.example.productcatalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Void> deleteProduct(UUID productId) {
        return productRepository.deleteById(productId);
    }

    public Mono<Product> updateProduct(UUID productId, Product updatedProduct) {
        return productRepository.findById(productId)
                .flatMap(product -> {
                    product.setName(updatedProduct.getName());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setImage(updatedProduct.getImage());
                    product.setDateAdded(updatedProduct.getDateAdded());
                    product.setActive(updatedProduct.isActive());

                    return productRepository.save(product);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    public Flux<Product> searchProducts(UUID categoryId, String name, Double minPrice, Double maxPrice) {
        return productRepository.findByFilters(categoryId, name, minPrice, maxPrice);
    }

   public Flux<Product> findAllByIdAndCategory(Category category) {
        return productRepository.findAllByCategoryId(category.getId());
   }


}
