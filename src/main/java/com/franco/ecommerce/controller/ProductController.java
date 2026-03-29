package com.franco.ecommerce.controller;

import com.franco.ecommerce.dto.ProductDTO;
import com.franco.ecommerce.model.Product;
import com.franco.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public Product getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductDTO dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setStock(dto.getStock());
        p.setPrice(dto.getPrice());
        return productService.createProduct(p);
    }

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> createProducts(
            @RequestBody List<ProductDTO> dtos) {
        List<Product> created = new ArrayList<>();
        List<String> skipped = new ArrayList<>();

        for (ProductDTO dto : dtos) {
            try {
                Product p = new Product();
                p.setName(dto.getName());
                p.setStock(dto.getStock());
                p.setPrice(dto.getPrice());
                created.add(productService.createProduct(p));
            } catch (IllegalArgumentException e) {
                skipped.add(dto.getName() + ": " + e.getMessage());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("created", created);
        response.put("skipped", skipped);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setStock(dto.getStock());
        p.setPrice(dto.getPrice());
        return productService.updateProduct(id, p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}