package com.example.studyspot.service;

import com.example.studyspot.model.Instructor;
import com.example.studyspot.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public Instructor registerInstructor(Instructor instructor) {

        if (instructorRepository.findByEmail(instructor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }


        return instructorRepository.save(instructor);
    }
}
