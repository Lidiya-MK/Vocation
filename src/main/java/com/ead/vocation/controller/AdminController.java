package com.ead.vocation.controller;

import com.ead.vocation.model.Admin;
import com.ead.vocation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<Admin> registerInstructor(@RequestBody Admin admin) {
        try {
            Admin registeredAdmin = adminService.registerAdmin(admin);
            return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
