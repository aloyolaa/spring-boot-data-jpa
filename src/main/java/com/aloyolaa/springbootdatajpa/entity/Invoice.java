package com.aloyolaa.springbootdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "observation")
    private String observation;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id", nullable = false)
    private List<InvoiceItem> invoiceItems = new ArrayList<>();

    public void addInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.add(invoiceItem);
    }

    public Double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (InvoiceItem invoiceItem : invoiceItems) {
            totalAmount = totalAmount + invoiceItem.calculateAmount();
        }
        return totalAmount;
    }

}