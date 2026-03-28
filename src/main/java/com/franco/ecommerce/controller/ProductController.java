package com.franco.ecommerce.controller;

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

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> createProducts(@RequestBody List<Product> products) {
        List<Product> created = new ArrayList<>();
        List<String> skipped = new ArrayList<>();

        for (Product product : products) {
            try {
                created.add(productService.createProduct(product));
            } catch (IllegalArgumentException e) {
                skipped.add(product.getName() + ": " + e.getMessage());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("created", created);
        response.put("skipped", skipped);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") //Fetches an exact match by its ID
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search") //Applies a filter, in this case, the product name. E.g., /search?name=Snack
    public Product getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product p) {
        return productService.createProduct(p);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product prod) {
        return productService.updateProduct(id, prod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
