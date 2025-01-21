package com.ead.vocation.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = true)
    private String profilePicture;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}