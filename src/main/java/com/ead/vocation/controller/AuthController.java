package com.ead.vocation.controller;

import com.ead.vocation.dtos.FreelancerResponse;
import com.ead.vocation.dtos.JobPosterResponse;
import com.ead.vocation.dtos.LoginRequest;
import com.ead.vocation.dtos.LoginResponse;
import com.ead.vocation.dtos.SignupFreelancerRequest;
import com.ead.vocation.dtos.SignupJobPosterRequest;
import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.JobPoster;
import com.ead.vocation.model.User;
import com.ead.vocation.service.AuthService;
import com.ead.vocation.service.FreelancerService;
import com.ead.vocation.service.JobPosterService;
import com.ead.vocation.shared.enums.Role;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private FreelancerService freelancerService;

    @Autowired
    private JobPosterService jobPosterService;

    @PostMapping("/login")
    public ResponseEntity<?> loginFreelancers(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/freelancers/signup")
    public ResponseEntity<?> signupFreelancers(@Valid @RequestBody SignupFreelancerRequest freelancerRequest) {
        User registeredUser;
        Freelancer registeredFreelancer;
        try {
            User user = new User();
            user.setEmail(freelancerRequest.getEmail());
            user.setPassword(freelancerRequest.getPassword());
            user.setRole(Role.FREELANCER);
            user.setName(freelancerRequest.getName());
            registeredUser = authService.register(user);

            Freelancer freelancer = new Freelancer();
            freelancer.setSkills(freelancerRequest.getSkills());
            freelancer.setIndustry(freelancerRequest.getIndustry());
            freelancer.setResumeLink(freelancerRequest.getResumeLink());
            freelancer.setCoverLetter(freelancerRequest.getCoverLetter());
            freelancer.setLinks(freelancerRequest.getLinks());
            freelancer.setDescription(freelancerRequest.getDescription());
            freelancer.setLocation(freelancerRequest.getLocation());
            freelancer.setPhoneNumber(freelancerRequest.getPhoneNumber());
            freelancer.setProfilePicture(freelancerRequest.getProfilePicture());
            freelancer.setYearsOfExperience(freelancerRequest.getYearsOfExperience());
            registeredFreelancer = freelancerService.createFreelancer(freelancer, registeredUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }

        FreelancerResponse freelancerResponse = new FreelancerResponse();
        freelancerResponse.setFields(registeredFreelancer, registeredUser);
        return ResponseEntity.ok(freelancerResponse);
    }

    @PostMapping("/job-posters/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupJobPosterRequest jobPosterRequest) {
        User registeredUser;
        JobPoster registeredJobPoster;
        try {
            User user = new User();
            user.setEmail(jobPosterRequest.getEmail());
            user.setPassword(jobPosterRequest.getPassword());
            user.setRole(Role.JOB_POSTER);
            user.setName(jobPosterRequest.getName());
            registeredUser = authService.register(user);

            JobPoster jobPoster = new JobPoster();
            jobPoster.setIndustry(jobPosterRequest.getIndustry());
            jobPoster.setLinks(jobPosterRequest.getLinks());
            jobPoster.setDescription(jobPosterRequest.getDescription());
            jobPoster.setLocation(jobPosterRequest.getLocation());
            jobPoster.setPhoneNumber(jobPosterRequest.getPhoneNumber());
            jobPoster.setProfilePicture(jobPosterRequest.getProfilePicture());
            registeredJobPoster = jobPosterService.createJobPoster(jobPoster, registeredUser.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }

        JobPosterResponse jobPosterResponse = new JobPosterResponse();
        jobPosterResponse.setFields(registeredJobPoster, registeredUser);
        return ResponseEntity.ok(jobPosterResponse);
    }
}
