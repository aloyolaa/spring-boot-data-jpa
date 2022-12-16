package com.aloyolaa.springbootdatajpa.service;

import com.aloyolaa.springbootdatajpa.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();

    Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findById(Long id);

    Optional<Customer> fetchByIdWithInvoice(Long id);

    void save(Customer customer);

    void delete(Long id);

}
