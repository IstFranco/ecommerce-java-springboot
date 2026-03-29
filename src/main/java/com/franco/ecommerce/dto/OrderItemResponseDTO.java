package com.franco.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
}
