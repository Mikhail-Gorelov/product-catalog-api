package com.example.productcatalog;

import com.example.productcatalog.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PostProxyIntegrationTest {
    @Test
    void postProxyInvoked() {
        assertTrue(CategoryService.POST_PROXY_INVOKE_COUNT.get() > 0);
    }
}
