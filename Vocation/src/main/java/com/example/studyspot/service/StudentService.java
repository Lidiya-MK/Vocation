package com.example.studyspot.service;

import com.example.studyspot.model.Student;
import com.example.studyspot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student registerStudent(Student student) {

        if (studentRepository.findByUsername(student.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }


        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }


        return studentRepository.save(student);
    }
}
