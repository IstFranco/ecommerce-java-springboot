package com.franco.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;
}