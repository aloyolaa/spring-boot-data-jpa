package com.aloyolaa.springbootdatajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities", uniqueConstraints = {
        @UniqueConstraint(name = "uc_authority_user_id_name", columnNames = {"user_id", "name"})
})
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

}