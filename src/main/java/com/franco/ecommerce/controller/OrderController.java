package com.franco.ecommerce.controller;

import com.franco.ecommerce.dto.OrderItemResponseDTO;
import com.franco.ecommerce.dto.OrderRequestDTO;
import com.franco.ecommerce.dto.OrderResponseDTO;
import com.franco.ecommerce.model.Order;
import com.franco.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return toDTO(orderService.getOrderById(id));
    }

    @PostMapping
    public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO orderReqDTO) {
        return toDTO(orderService.createOrder(orderReqDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    private OrderResponseDTO toDTO(Order o) {
        return OrderResponseDTO.builder()
                .orderId(o.getOrderId())
                .date(o.getDate())
                .total(o.getTotal())
                .customer(o.getCustomer() == null ? null :
                        com.franco.ecommerce.dto.CustomerResponseDTO.builder()
                                .customerId(o.getCustomer().getCustomerId())
                                .firstName(o.getCustomer().getFirstName())
                                .lastName(o.getCustomer().getLastName())
                                .email(o.getCustomer().getEmail())
                                .dni(o.getCustomer().getDni())
                                .role(o.getCustomer().getRole())
                                .build())
                .items(o.getOrderItems() == null ? List.<OrderItemResponseDTO>of() :
                        o.getOrderItems().stream()
                                .map(item -> OrderItemResponseDTO.builder()
                                        .productId(item.getProduct().getProductId())
                                        .productName(item.getProduct().getName())
                                        .quantity(item.getQuantity())
                                        .unitPrice(item.getUnitPrice())
                                        .build())
                                .toList())
                .build();
    }
}