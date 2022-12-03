package com.aloyolaa.springbootdatajpa.service;

import com.aloyolaa.springbootdatajpa.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    void save(Customer customer);

    void delete(Long id);

}
