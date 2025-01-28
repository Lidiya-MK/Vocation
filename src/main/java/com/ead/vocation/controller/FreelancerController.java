package com.ead.vocation.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;


import com.ead.vocation.dtos.ApplicationRequest;
import com.ead.vocation.dtos.ApplicationResponse;
import com.ead.vocation.dtos.FreelancerResponse;
import com.ead.vocation.dtos.FreelancerUpdateRequest;
import com.ead.vocation.dtos.JobResponse;
import com.ead.vocation.model.Application;
import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.User;
import com.ead.vocation.service.FreelancerService;
import com.ead.vocation.service.JobService;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestHeader;


import com.ead.vocation.shared.util.JwtServices;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/freelancers")
public class FreelancerController {


    @Autowired
    private JwtServices jwtServices;

    
    @Autowired
    private FreelancerService freelancerService;

   @Autowired
    private JobService jobService;



    @GetMapping("/profile")
    public String getProfile() {
        return "freelancer profile";
    }



    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobResponses = jobService.getAllJobs();
        return new ResponseEntity<>(jobResponses, HttpStatus.OK);
    }


    @PostMapping("/apply")
public ResponseEntity<?> applyForJob(@Valid @RequestBody ApplicationRequest applicationRequest,
                                     @RequestHeader("Authorization") String token) {
    try {

        Integer freelancerId = jwtServices.extractIdFromHeader(token);
        
     
        Application application = freelancerService.createApplication(applicationRequest, freelancerId);

        JobResponse jobResponse = new JobResponse();
        jobResponse.setFields(application.getJob());

        Freelancer freelancer = application.getFreelancer();
        User user = freelancer.getUser(); 

        FreelancerResponse freelancerResponse = new FreelancerResponse();
        freelancerResponse.setFields(freelancer, user);

        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setFields(application, jobResponse, freelancerResponse);
        
      
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    } catch (IllegalArgumentException e) {
   
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}



@GetMapping("/applications")
public ResponseEntity<?> getAllApplicationsByFreelancer(@RequestHeader("Authorization") String token) {
    try {
   
        Integer freelancerId = jwtServices.extractIdFromHeader(token);
        
   
        List<Application> applications = freelancerService.getAllApplicationsByFreelancerId(freelancerId);
        
        
        List<ApplicationResponse> applicationResponses = applications.stream()
                .map(application -> {
                
                    JobResponse jobResponse = new JobResponse();
                    jobResponse.setFields(application.getJob());

                    Freelancer freelancer = application.getFreelancer();
                    User user = freelancer.getUser();

                    FreelancerResponse freelancerResponse = new FreelancerResponse();
                    freelancerResponse.setFields(freelancer, user);
                    

                    ApplicationResponse applicationResponse = new ApplicationResponse();
                    applicationResponse.setFields(application, jobResponse, freelancerResponse);
                    
                    return applicationResponse;
                })
                .collect(Collectors.toList());
        
      
        return ResponseEntity.ok(applicationResponses);
        
    } catch (IllegalArgumentException e) {
       
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
    }
}



@GetMapping("/info")
public ResponseEntity<?> getFreelancerProfile(@RequestHeader("Authorization") String token) {
    try {
       
        Integer userId = jwtServices.extractIdFromHeader(token);

  
        FreelancerResponse freelancerResponse = freelancerService.getFreelancerProfile(userId);

        return ResponseEntity.ok(freelancerResponse);
    } catch (IllegalArgumentException e) {
    
        return ResponseEntity.status(400).body("Invalid token or user not found");
    } catch (Exception e) {
        
        return ResponseEntity.status(500).body("An error occurred while fetching the profile");
    }
}




@PutMapping("/update")
public ResponseEntity<?> updateFreelancerProfile(
        @RequestBody FreelancerUpdateRequest freelancerUpdateRequest,
        @RequestHeader("Authorization") String token) {
    try {
   
        Integer userId = jwtServices.extractIdFromHeader(token);

    
        FreelancerResponse updatedFreelancerResponse = freelancerService.updateFreelancerProfile(userId, freelancerUpdateRequest);

     
        return ResponseEntity.ok(updatedFreelancerResponse);
    } catch (IllegalArgumentException e) {
       
        return ResponseEntity.status(400).body("Invalid token or user not found");
    } catch (Exception e) {
      
        return ResponseEntity.status(500).body("An error occurred while updating the profile");
    }
}





}
