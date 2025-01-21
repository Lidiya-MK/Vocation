package com.ead.vocation.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.ead.vocation.shared.enums.ApplicationStatus;

@Entity
@Data
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        submittedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}