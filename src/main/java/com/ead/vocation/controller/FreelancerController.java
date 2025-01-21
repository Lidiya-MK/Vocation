package com.ead.vocation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/freelancers")
public class FreelancerController {
    @GetMapping("/profile")
    public String getProfile() {
        return "freelancer profile";
    }
}
