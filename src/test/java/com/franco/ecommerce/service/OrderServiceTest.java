package com.franco.ecommerce.service;

import com.franco.ecommerce.dto.OrderItemDTO;
import com.franco.ecommerce.dto.OrderRequestDTO;
import com.franco.ecommerce.model.Customer;
import com.franco.ecommerce.model.Order;
import com.franco.ecommerce.model.Product;
import com.franco.ecommerce.repository.CustomerRepository;
import com.franco.ecommerce.repository.OrderRepository;
import com.franco.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_CustomerNotFound_ThrowsException() {
        //Given
        Long fakeCustomerId = 99L;
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setIdCustomer(fakeCustomerId);

        //When & Then
        when(customerRepository.findById(fakeCustomerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(requestDTO);
        });

        assertEquals("Customer not found with ID: " + fakeCustomerId, exception.getMessage());

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_NotEnoughStock_ThrowsException() {
        Long fakeCustomerId = 1L;
        Customer fakeCustomer = new Customer();
        fakeCustomer.setCustomerId(fakeCustomerId);

        Long fakeProductId = 10L;
        Product fakeProduct = new Product();
        fakeProduct.setProductId(fakeProductId);
        fakeProduct.setName("Zapatillas Nike");
        fakeProduct.setPrice(100000.0);
        fakeProduct.setStock(2);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setIdCustomer(fakeCustomerId);

        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setIdProduct(fakeProductId);
        itemDTO.setQuantity(5);

        requestDTO.setItems(List.of(itemDTO));

        when(customerRepository.findById(fakeCustomerId)).thenReturn(Optional.of(fakeCustomer));
        when(productRepository.findById(fakeProductId)).thenReturn((Optional.of(fakeProduct)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder((requestDTO));
        });

        assertEquals("Not enough stock for product: Zapatillas Nike", exception.getMessage());

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_Success() {

        Long fakeCustomerId = 1L;
        Customer caleb = new Customer();
        caleb.setCustomerId(fakeCustomerId);

        Long fakeProductId = 10L;
        Product fakeProduct = new Product();
        fakeProduct.setProductId(fakeProductId);
        fakeProduct.setPrice(50000.0);
        fakeProduct.setStock(10);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setIdCustomer(fakeCustomerId);

        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setIdProduct(fakeProductId);
        itemDTO.setQuantity(2);

        requestDTO.setItems(List.of(itemDTO));

        when(customerRepository.findById(fakeCustomerId)).thenReturn(Optional.of(caleb));
        when(productRepository.findById(fakeProductId)).thenReturn(Optional.of(fakeProduct));

        Order mockOrder = new Order();
        mockOrder.setTotal(100000.0);// $50000 x 2 = $100000
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order result = orderService.createOrder(requestDTO);

        assertEquals(100000.0, result.getTotal());

        assertEquals(8, fakeProduct.getStock()); // 10 prod - 2 prod = 8 products left

        verify(orderRepository, times(1)).save(any(Order.class));

        verify(productRepository, times(1)).save(fakeProduct);
    }

}
