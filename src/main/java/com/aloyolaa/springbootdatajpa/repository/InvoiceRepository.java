package com.aloyolaa.springbootdatajpa.repository;

import com.aloyolaa.springbootdatajpa.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("select i from Invoice i join fetch i.customer c join fetch i.invoiceItems ii join fetch ii.product where i.id = ?1")
    Optional<Invoice> fetchByIdWithCustomerWithInvoiceItemWithProduct(Long id);

}