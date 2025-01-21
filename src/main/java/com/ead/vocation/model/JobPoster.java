package com.ead.vocation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class JobPoster extends User {
    @ElementCollection
    private List<String> links;

    @Column(nullable = false)
    private String industry;

    @Column(nullable = true)
    private Double rating;
}
