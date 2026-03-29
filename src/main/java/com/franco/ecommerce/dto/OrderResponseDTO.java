package com.franco.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime date;
    private double total;
    private CustomerResponseDTO customer;
    private List<OrderItemResponseDTO> items;
}
