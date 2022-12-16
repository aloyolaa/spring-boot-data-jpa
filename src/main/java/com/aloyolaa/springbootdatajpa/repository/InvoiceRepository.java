package com.aloyolaa.springbootdatajpa.repository;

import com.aloyolaa.springbootdatajpa.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}