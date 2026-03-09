package com.franco.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    @NotNull(message = " Product ID is mandatory")
    private Long idProduct;

    @Min(value = 1, message = "Minimum quantity must be 1")
    private int quantity;
}
