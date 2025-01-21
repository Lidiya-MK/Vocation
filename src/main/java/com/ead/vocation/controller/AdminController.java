package com.ead.vocation.controller;

import com.ead.vocation.dtos.LoginRequest;
import com.ead.vocation.model.Admin;
import com.ead.vocation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/dashboard")
    public String getMethodName() {
        return "admin test dashboard";
    }

    // @PostMapping("/signup")
    // public ResponseEntity<Admin> registerInstructor(@RequestBody Admin admin) {
    // try {
    // Admin registeredAdmin = adminService.registerAdmin(admin);
    // return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
    // } catch (IllegalArgumentException e) {
    // return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    // }
    // }
}
