package com.aloyolaa.springbootdatajpa.repository;

import com.aloyolaa.springbootdatajpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}