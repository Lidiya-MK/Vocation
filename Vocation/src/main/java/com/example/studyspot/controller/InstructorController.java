

package com.example.studyspot.controller;

import com.example.studyspot.model.Instructor;
import com.example.studyspot.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/signup")
    public ResponseEntity<Instructor> registerInstructor(@RequestBody Instructor instructor) {
        try {
            Instructor registeredInstructor = instructorService.registerInstructor(instructor);
            return new ResponseEntity<>(registeredInstructor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
