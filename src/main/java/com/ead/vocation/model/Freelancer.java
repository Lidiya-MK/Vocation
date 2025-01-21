package com.ead.vocation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Freelancer extends User {
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

    @Column(nullable = true)
    private Double rating;
}
