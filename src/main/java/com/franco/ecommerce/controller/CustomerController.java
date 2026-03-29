package com.franco.ecommerce.controller;

import com.franco.ecommerce.dto.CustomerResponseDTO;
import com.franco.ecommerce.dto.CustomerUpdateDTO;
import com.franco.ecommerce.dto.RegisterRequest;
import com.franco.ecommerce.enums.Role;
import com.franco.ecommerce.model.Customer;
import com.franco.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        return toDTO(customerService.getCustomerById(id));
    }

    @GetMapping("/me")
    public CustomerResponseDTO getMyProfile() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return toDTO(customerService.getCustomerByEmail(email));
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(@RequestBody RegisterRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dni(request.getDni())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        return toDTO(customerService.createCustomer(customer));
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(@PathVariable Long id,
                                              @RequestBody CustomerUpdateDTO request) {
        Customer existing = customerService.getCustomerById(id);
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setDni(request.getDni());
        return toDTO(customerService.updateCustomer(id, existing));
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }

    private CustomerResponseDTO toDTO(Customer c) {
        return CustomerResponseDTO.builder()
                .customerId(c.getCustomerId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .email(c.getEmail())
                .dni(c.getDni())
                .role(c.getRole())
                .build();
    }
}