package com.example.studyspot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data 
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; 

    private String app;

    @ElementCollection
    private List<Integer> studentIds;

    @ElementCollection
    private List<Integer> instructorIds;
}
