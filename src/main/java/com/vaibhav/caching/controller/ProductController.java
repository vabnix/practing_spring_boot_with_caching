package com.vaibhav.caching.controller;

import com.vaibhav.caching.model.Product;
import com.vaibhav.caching.service.ProductService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProducts(@PathVariable Long id) {
        return productService.getProducts(id);
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id) {
        return productService.updateProduct(id);
    }


}
