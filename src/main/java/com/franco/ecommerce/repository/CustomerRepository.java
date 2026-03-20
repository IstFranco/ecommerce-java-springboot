package com.franco.ecommerce.repository;

import com.franco.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByDni(String dni);
    Optional<Customer> findByEmail(String email);

}
