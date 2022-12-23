package com.aloyolaa.springbootdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @PastOrPresent
    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.zzz")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createDate = LocalDateTime.now();

    @JsonManagedReference
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();

    public void addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
    }

}
