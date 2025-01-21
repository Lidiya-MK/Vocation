package com.ead.vocation.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ead.vocation.shared.enums.JobStatus;
import com.ead.vocation.shared.enums.JobType;

@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_poster_id", nullable = false)
    private JobPoster jobPoster;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType type;

    @ElementCollection
    private List<String> skillsRequired;

    @Column(nullable = false)
    private Double budget;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = true)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDate applicationDeadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

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