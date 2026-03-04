package com.franco.ecommerce.controller;

import com.franco.ecommerce.model.Product;
import com.franco.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
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
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
