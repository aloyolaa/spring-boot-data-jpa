package com.aloyolaa.springbootdatajpa.repository;

import com.aloyolaa.springbootdatajpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where upper(p.name) like upper(concat('%', ?1, '%'))")
    List<Product> findByNameContainsIgnoreCase(String name);

}