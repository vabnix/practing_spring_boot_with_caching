package com.vaibhav.caching;

import com.vaibhav.caching.model.Product;
import com.vaibhav.caching.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cache.type=caffeine",
        "spring.cache.caffeine.spec=maximumSize=100,expireAfterWrite=60m"
})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testCachingPerformance() {
        // first call should be slow
        long startTime = System.currentTimeMillis();
        Product product1 = productService.getProducts(1L);
        long firstExecutionTime = System.currentTimeMillis() - startTime;

        // second call should be fast
        startTime = System.currentTimeMillis();
        Product product2 = productService.getProducts(1L);
        long secondExecutionTime = System.currentTimeMillis() - startTime;

        // verifying the product is cached and equal
       assertEquals(product1.getId(), product2.getId());
       assertEquals(product1.getName(), product2.getName());
       assertEquals(product1.getPrice(), product2.getPrice());

         // verifying the first call is slow and second call is fast
        assertTrue(firstExecutionTime > secondExecutionTime);
        assertTrue(secondExecutionTime < 100);
    }

    @Test
    public void testCacheEviction() {
        productService.getProducts(1L);
        assertTrue(cacheManager.getCache("products").get(1L) != null);

        productService.updateProduct(1L);
        assertTrue(cacheManager.getCache("products").get(1L) == null);

        // Call with different IDs to test cache misses
        long startTime = System.currentTimeMillis();
        Product product1 = productService.getProducts(4L);
        long firstCallDuration = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        Product product2 = productService.getProducts(3L);
        long secondCallDuration = System.currentTimeMillis() - startTime;

        // Both calls should be slow as they're different products
        assertTrue(firstCallDuration >= 2900, "First call should not be cached");
        assertTrue(secondCallDuration >= 2900, "Second call should not be cached");
    }

    @Test
    public void testCacheContent() {
        // Cache a product
        Product product = productService.getProducts(5L);

        // Get the cached value directly from cache manager
        Product cachedProduct = cacheManager.getCache("products")
                .get(5L, Product.class);

        // Verify cached content
        assertNotNull(cachedProduct);
        assertEquals(product.getId(), cachedProduct.getId());
        assertEquals(product.getName(), cachedProduct.getName());
        assertEquals(product.getPrice(), cachedProduct.getPrice());
    }


}
