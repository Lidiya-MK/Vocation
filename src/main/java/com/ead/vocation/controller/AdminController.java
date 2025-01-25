package com.ead.vocation.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @GetMapping("/dashboard")
    public String getMethodName() {
        return "admin test dashboard";
    }
}
