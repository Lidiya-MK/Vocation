package com.ead.vocation.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ead.vocation.model.Job;
import com.ead.vocation.model.JobPoster;
import com.ead.vocation.model.User;
import com.ead.vocation.service.FreelancerService;
import com.ead.vocation.service.JobService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ead.vocation.shared.ErrorResponse;
import com.ead.vocation.shared.util.JwtServices;

import jakarta.servlet.http.HttpServletRequest;
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



   

    @GetMapping("/dashboard")
    public String getFreelancerDashboard(HttpServletRequest request, Model model) {
        String token = (String) request.getAttribute("Authorization");
        Integer id = jwtServices.extractIdFromHeader(token); 
        FreelancerResponse freelancerResponse = freelancerService.getFreelancerProfile(id);
        List<Application> applications = freelancerService.getAllApplicationsByFreelancerId(id);
        List<JobResponse> jobResponses = jobService.getAllJobs();
        
        model.addAttribute("freelancerName", freelancerResponse.getName());
        model.addAttribute("freelancerEmail", freelancerResponse.getEmail());
        model.addAttribute("freelancerIndustry", freelancerResponse.getIndustry());
        model.addAttribute("freelancerLocation", freelancerResponse.getLocation());
        model.addAttribute("freelancerDescription", freelancerResponse.getDescription());
        model.addAttribute("freelancerProfilePicture", freelancerResponse.getProfilePicture());
        model.addAttribute("applications", applications);
        model.addAttribute("availableJobs", jobResponses);

        return "freelancer-home";
    }


     @GetMapping("/profile")
    public String getFreelancerProfile(HttpServletRequest request, Model model) {
        String token = (String) request.getAttribute("Authorization");
        Integer id = jwtServices.extractIdFromHeader(token); 
        FreelancerResponse freelancerResponse = freelancerService.getFreelancerProfile(id);
        model.addAttribute("name", freelancerResponse.getName());
        model.addAttribute("industry", freelancerResponse.getIndustry());
        model.addAttribute("description", freelancerResponse.getDescription());
        model.addAttribute("email", freelancerResponse.getEmail());
        model.addAttribute("location", freelancerResponse.getLocation());
        model.addAttribute("location", freelancerResponse.getLocation());
        model.addAttribute("phoneNumber", freelancerResponse.getPhoneNumber());
        model.addAttribute("links", freelancerResponse.getLinks());
        model.addAttribute("resume", freelancerResponse.getResumeLink());
        model.addAttribute("profilePicture", freelancerResponse.getProfilePicture());
        model.addAttribute("skills", freelancerResponse.getSkills());
        model.addAttribute("experience", freelancerResponse.getYearsOfExperience());
        return "freelancer-update-profile";
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

@DeleteMapping("/applications/withdraw/{applicationId}")
public ResponseEntity<?> deleteApplicationById(
        @PathVariable Integer applicationId,
        @RequestHeader("Authorization") String token) {
    try {
        Integer freelancerId = jwtServices.extractIdFromHeader(token);
        freelancerService.deleteApplicationByIdAndFreelancerId(applicationId, freelancerId);
        
      
        Map<String, String> response = new HashMap<>();
        response.put("message", "Application withdrawn successfully");
        response.put("applicationId", String.valueOf(applicationId));

        return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
        return handleException(e);
    }
}

    private ResponseEntity<?> handleException(IllegalArgumentException e) {
        HttpStatus status = e.getMessage().contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ErrorResponse(e.getMessage()));
    }


}
