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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        when(authentication.getName()).thenReturn("test@email.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createOrder_CustomerNotFound_ThrowsException() {
        //Given
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setItems(List.of());

        //When & Then
        when(customerRepository.findByEmail("test@email.com")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(requestDTO);
        });

        assertEquals("Customer not found with email: test@email.com", exception.getMessage());

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_NotEnoughStock_ThrowsException() {
        Customer fakeCustomer = new Customer();
        fakeCustomer.setCustomerId(1L);

        Long fakeProductId = 10L;
        Product fakeProduct = new Product();
        fakeProduct.setProductId(fakeProductId);
        fakeProduct.setName("Zapatillas Nike");
        fakeProduct.setPrice(100000.0);
        fakeProduct.setStock(2);

        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setIdProduct(fakeProductId);
        itemDTO.setQuantity(5);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setItems(List.of(itemDTO));

        when(customerRepository.findByEmail("test@email.com")).thenReturn(Optional.of(fakeCustomer));
        when(productRepository.findById(fakeProductId)).thenReturn((Optional.of(fakeProduct)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder((requestDTO));
        });

        assertEquals("Not enough stock for product: Zapatillas Nike", exception.getMessage());

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_Success() {

        Customer caleb = new Customer();
        caleb.setCustomerId(1L);

        Long fakeProductId = 10L;
        Product fakeProduct = new Product();
        fakeProduct.setProductId(fakeProductId);
        fakeProduct.setPrice(50000.0);
        fakeProduct.setStock(10);

        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setIdProduct(fakeProductId);
        itemDTO.setQuantity(2);

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setItems(List.of(itemDTO));

        when(customerRepository.findByEmail("test@email.com")).thenReturn(Optional.of(caleb));
        when(productRepository.findById(fakeProductId)).thenReturn(Optional.of(fakeProduct));

        Order mockOrder = new Order();
        mockOrder.setTotal(100000.0);// $50000 x 2 = $100000
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        Order result = orderService.createOrder(requestDTO);

        assertEquals(100000.0, result.getTotal());
        assertEquals(8, fakeProduct.getStock()); // 10 prod - 2 prod = 8 products left
        verify(orderRepository, times(1)).save(any(Order.class));
    }

}
