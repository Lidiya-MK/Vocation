package com.ead.vocation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    private List<String> skills;

    @Column(nullable = false)
    private String industry;

    @Column(nullable = false)
    private Integer yearsOfExperience;

    @Column(nullable = true)
    private String resumeLink;

    @Column(nullable = true)
    private String coverLetter;

    @ElementCollection
    private List<String> links;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = true)
    private String profilePicture;

    @Column(nullable = true)
    private Double rating;
}
