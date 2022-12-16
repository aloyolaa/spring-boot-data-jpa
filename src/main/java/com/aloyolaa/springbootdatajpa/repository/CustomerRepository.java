package com.aloyolaa.springbootdatajpa.repository;

import com.aloyolaa.springbootdatajpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c left join fetch c.invoices i where c.id = ?1")
    Optional<Customer> fetchByIdWithInvoice(Long id);

}