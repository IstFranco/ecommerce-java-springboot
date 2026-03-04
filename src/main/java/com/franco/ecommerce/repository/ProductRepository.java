package com.franco.ecommerce.repository;

import com.franco.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //@Query("SELECT p FROM Product p WHERE p.name = :name") Is not necesary.
    Optional<Product> findByName(@Param("name") String name); //Optional returns object only if this exists

    boolean existsByName(String name);

}
