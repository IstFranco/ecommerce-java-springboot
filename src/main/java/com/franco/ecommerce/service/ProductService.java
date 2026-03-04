package com.franco.ecommerce.service;

import com.franco.ecommerce.model.Product;
import com.franco.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Product not found with name: " + name));
    }

    public Product createProduct(Product prod) {
        if (productRepository.existsByName(prod.getName())) {
            throw new IllegalArgumentException("A product with name: " + prod.getName() + " already exists.");
        }
        if (prod.getPrice() < 0 || prod.getStock() < 0) {
            throw new IllegalArgumentException("Price and stock cannot be negative.");
        }
        return productRepository.save(prod);
    }


    public Product updateProduct(Long id, Product prod) {
        Product existingProduct = getProductById(id);

        if (!existingProduct.getName().equals(prod.getName()) &&
                productRepository.existsByName(prod.getName())) {
            throw new IllegalArgumentException("A product with name " + prod.getName() + " already exists.");
        }
        existingProduct.setName(prod.getName());
        existingProduct.setStock(prod.getStock());
        existingProduct.setPrice(prod.getPrice());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

}
