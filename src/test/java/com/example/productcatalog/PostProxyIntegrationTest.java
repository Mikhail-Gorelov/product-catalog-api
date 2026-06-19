package com.example.productcatalog;

import com.example.productcatalog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostProxyIntegrationTest {
    @Autowired
    private CategoryService categoryService;


    @Test
    void postProxyInvoked() {
        assertTrue(categoryService.getPostProxyInvokeCount() > 0);
    }
}
