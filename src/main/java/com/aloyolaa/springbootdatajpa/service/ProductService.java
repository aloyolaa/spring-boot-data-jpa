package com.aloyolaa.springbootdatajpa.service;

import com.aloyolaa.springbootdatajpa.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    List<Product> findByNameContainsIgnoreCase(String name);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    void save(Product product);

    void delete(Long id);

}
