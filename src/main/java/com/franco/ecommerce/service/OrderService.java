package com.franco.ecommerce.service;

import com.franco.ecommerce.dto.OrderItemDTO;
import com.franco.ecommerce.dto.OrderRequestDTO;
import com.franco.ecommerce.model.Customer;
import com.franco.ecommerce.model.Order;
import com.franco.ecommerce.model.OrderItem;
import com.franco.ecommerce.model.Product;
import com.franco.ecommerce.repository.CustomerRepository;
import com.franco.ecommerce.repository.OrderRepository;
import com.franco.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    @Transactional // If any step fails, the purchase process is rolled back to ensure data consistency.
    public Order createOrder(OrderRequestDTO ordReqdto) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Customer not found with email: " + email));

        Order order = new Order();
        order.setCustomer(customer);
        order.setDate(java.time.LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (OrderItemDTO itemDTO : ordReqdto.getItems()) {
            Product product = productRepository.findById(itemDTO.getIdProduct()).orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + itemDTO.getIdProduct()));

            if (product.getStock() < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Product stock less than or equal to stock amount");
            }

            product.setStock(product.getStock() - itemDTO.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
            totalAmount += (product.getPrice() * itemDTO.getQuantity());
        }

        order.setOrderItems(orderItems);
        order.setTotal(totalAmount);

        return orderRepository.save(order);
    }

    @Transactional // We use Transactional in case any stock update fails
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);

        //Restore product stock (Restock)
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            //Add the ordered quantity back to the current stock
            product.setStock(product.getStock() + item.getQuantity());
        }

        //Now that the stock is safely restored, delete the order
        //(Because of CascadeType.ALL in the model, this automatically deletes the OrderItems as well)
        orderRepository.delete(order);
    }

}
