package com.ead.vocation.controller;

import com.ead.vocation.model.Student;
import com.ead.vocation.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/signup")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        try {
            Student registeredStudent = studentService.registerStudent(student);
            return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
