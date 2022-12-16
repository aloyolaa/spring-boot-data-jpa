package com.aloyolaa.springbootdatajpa.service;

import com.aloyolaa.springbootdatajpa.entity.Invoice;

import java.util.Optional;

public interface InvoiceService {

    Optional<Invoice> fetchByIdWithCustomerWithInvoiceItemWithProduct(Long id);

    Optional<Invoice> findById(Long id);

    void save(Invoice invoice);

    void delete(Long id);

}
