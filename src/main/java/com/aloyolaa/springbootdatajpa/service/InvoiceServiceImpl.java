package com.aloyolaa.springbootdatajpa.service;

import com.aloyolaa.springbootdatajpa.entity.Invoice;
import com.aloyolaa.springbootdatajpa.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> fetchByIdWithCustomerWithInvoiceItemWithProduct(Long id) {
        return invoiceRepository.fetchByIdWithCustomerWithInvoiceItemWithProduct(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }

}
