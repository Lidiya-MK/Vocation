package com.ead.vocation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job-posters")
public class JobPosterController {
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "job poster dashboard";
    }
}
