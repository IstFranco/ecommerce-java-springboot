package com.franco.ecommerce.service;

import com.franco.ecommerce.model.Customer;
import com.franco.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    //CRUD
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("Customer not found with ID: " + id));
    }
    public Customer createCustomer(Customer cust){
        if (customerRepository.existsByDni(cust.getDni())) {
            throw new IllegalArgumentException("A customer whit DNI " + cust.getDni() + " already exists.");
        }
        return customerRepository.save(cust);
    }
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer existingCustomer = getCustomerById(id);

        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setLastName(customerDetails.getLastName());
        if (!existingCustomer.getDni().equals(customerDetails.getDni()) &&
            customerRepository.existsByDni(customerDetails.getDni())) {
            throw new IllegalArgumentException("The DNI " + customerDetails.getDni() + " is already in use.");
        }
        existingCustomer.setDni(customerDetails.getDni());

        return customerRepository.save(existingCustomer);
    }
    public String deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Custoer not found with ID: " + id);
        }
        customerRepository.deleteById(id);

        return "Customer deleted successfully";
    }
}
