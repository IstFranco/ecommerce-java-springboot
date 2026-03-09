package com.franco.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotNull(message = "Customer ID cannot be null")
    private Long idCustomer;

    @NotEmpty(message = "Item list cannot be empty")
    @Valid //This tells Spring to also validate the items inside the list
    private List<OrderItemDTO> items;
}
