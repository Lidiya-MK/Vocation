package com.example.studyspot.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data 
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String universityName;

    @Column(nullable = false)
    private String fieldOfStudy;

    @ElementCollection
    private List<Integer> tutorials;

    @ElementCollection
    private List<Integer> likedTutorials;

    private String profilePicture;

    @ElementCollection
    private List<Integer> comments;

    @Column(nullable = false)
    private String password; 
}
