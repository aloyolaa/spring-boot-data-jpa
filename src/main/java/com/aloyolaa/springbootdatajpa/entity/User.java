package com.aloyolaa.springbootdatajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uc_user_username", columnNames = {"username"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private List<Authority> authorities = new ArrayList<>();

}