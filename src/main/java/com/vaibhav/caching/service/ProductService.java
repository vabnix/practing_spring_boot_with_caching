package com.vaibhav.caching.service;

import com.vaibhav.caching.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Cacheable(value = "products", key = "#id")
    public Product getProducts(Long id) {
        logger.info("Getting product with id {}.", id);
        simulateSlowService();

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);
        logger.info("Returning product: {}", product);
        return product;
    }

    private void simulateSlowService() {
        try {
            logger.info("Sleeping for 3 seconds...");
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @CacheEvict(value = "products", key = "#id")
    public String updateProduct(Long id) {
        logger.info("Clearing cache for product with id {}.", id);
        return "Product with id " + id + " is updated";
    }
}
