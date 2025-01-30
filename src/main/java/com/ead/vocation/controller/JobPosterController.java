package com.ead.vocation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ead.vocation.dtos.ApplicationResponse;
import com.ead.vocation.dtos.FreelancerResponse;
import com.ead.vocation.dtos.JobPosterResponse;
import com.ead.vocation.dtos.JobRequest;
import com.ead.vocation.dtos.JobResponse;
import com.ead.vocation.dtos.UpdateApplicationStatusRequest;
import com.ead.vocation.dtos.UpdateJobPosterRequest;
import com.ead.vocation.model.Application;
import com.ead.vocation.model.Job;
import com.ead.vocation.model.JobPoster;
import com.ead.vocation.service.ApplicationService;
import com.ead.vocation.service.JobPosterService;
import com.ead.vocation.shared.ErrorResponse;
import com.ead.vocation.shared.util.JwtServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequestMapping("/job-posters")
public class JobPosterController {
    @Autowired
    private JwtServices jwtServices;

    @Autowired
    private JobPosterService jobPosterService;
    
    
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpServletRequest request, Model model) {
        String token = (String) request.getAttribute("Authorization");
        Integer id = jwtServices.extractIdFromHeader(token);
        JobPoster jobPoster = jobPosterService.getJobPoster(id);
        List<Job> jobs = jobPosterService.getAllJobsByPosterId(id);
        model.addAttribute("companyName", jobPoster.getUser().getName());
        model.addAttribute("companyEmail", jobPoster.getUser().getEmail());
        model.addAttribute("industryName", jobPoster.getIndustry());
        model.addAttribute("location", jobPoster.getLocation());
        model.addAttribute("companyDescription", jobPoster.getDescription());
        model.addAttribute("jobs", jobs);
        return "job-poster-home";
    }

    @GetMapping("/view-freelancer/{applicationId}")
    public String getFreelancerProfileFromApplication(@PathVariable Integer applicationId, Model model) {
        try {
         
            FreelancerResponse freelancerResponse = jobPosterService.getFreelancerByApplicationId(applicationId);

          
            model.addAttribute("name", freelancerResponse.getName());
            model.addAttribute("industry", freelancerResponse.getIndustry());
            model.addAttribute("description", freelancerResponse.getDescription());
            model.addAttribute("email", freelancerResponse.getEmail());
            model.addAttribute("location", freelancerResponse.getLocation());
            model.addAttribute("phoneNumber", freelancerResponse.getPhoneNumber());
            model.addAttribute("links", freelancerResponse.getLinks());
            model.addAttribute("resume", freelancerResponse.getResumeLink());
            model.addAttribute("profilePicture", freelancerResponse.getProfilePicture());
            model.addAttribute("skills", freelancerResponse.getSkills());
            model.addAttribute("experience", freelancerResponse.getYearsOfExperience());

     
            return "freelancer-profile-view";
        } catch (IllegalArgumentException e) {
           
            model.addAttribute("error", "Freelancer profile not found for this application");
            return "error-page";  
        }
    }

    @GetMapping("/job-details/{jobId}")
    public String getJobDetails(@PathVariable Integer jobId, HttpServletRequest request, Model model) {
        String token = (String) request.getAttribute("Authorization");
        Integer jobPosterId = jwtServices.extractIdFromHeader(token);
        Job job = jobPosterService.getJobByIdAndPosterId(jobId, jobPosterId);

        List<Application> applications = applicationService.getAllApplications(jobId, jobPosterId);
        StringBuilder skills = new StringBuilder();
        for (String skill : job.getSkillsRequired()) {
            skills.append(skill + ", ");
        }

        if (skills.length() > 0) {
            skills.setLength(skills.length() - 2);
        }

        model.addAttribute("skillsRequired", skills.toString());
        model.addAttribute("jobTitle", job.getTitle());
        model.addAttribute("jobId", job.getId());
        model.addAttribute("startDate", job.getStartDate());
        model.addAttribute("jobType", job.getType());
        model.addAttribute("jobDescription", job.getDescription());
        model.addAttribute("budget", job.getBudget());
        model.addAttribute("deadline", job.getApplicationDeadline());
        model.addAttribute("skills", skills.toString());
        model.addAttribute("applications", applications);
        return "job-poster-job-details";
    }

    @GetMapping("/profile")
    public String getJobPosterProfile(HttpServletRequest request, Model model) {
        String token = (String) request.getAttribute("Authorization");
        Integer jobPosterId = jwtServices.extractIdFromHeader(token);
        JobPoster jobPoster = jobPosterService.getJobPoster(jobPosterId);
        model.addAttribute("name", jobPoster.getUser().getName());
        model.addAttribute("industry", jobPoster.getIndustry());
        model.addAttribute("description", jobPoster.getDescription());
        model.addAttribute("email", jobPoster.getUser().getEmail());
        model.addAttribute("location", jobPoster.getLocation());
        model.addAttribute("phoneNumber", jobPoster.getPhoneNumber());
        model.addAttribute("links", jobPoster.getLinks());
        return "job-poster-update-profile";
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateJobPoster(@RequestHeader("Authorization") String token,
            @Valid @RequestBody UpdateJobPosterRequest entity) {
        try {
            Integer id = jwtServices.extractIdFromHeader(token);
            JobPoster jobPoster = jobPosterService.updateJobPoster(id, entity);
            JobPosterResponse jobPosterResponse = new JobPosterResponse();
            jobPosterResponse.setFields(jobPoster, jobPoster.getUser());
            return ResponseEntity.ok(jobPosterResponse);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    @GetMapping("/jobs")
    public ResponseEntity<?> getAllJobs(@RequestHeader("Authorization") String token) {
        try {
            Integer id = jwtServices.extractIdFromHeader(token);
            List<Job> jobs = jobPosterService.getAllJobsByPosterId(id);
            List<JobResponse> jobResponses = jobs.stream()
                    .map(job -> {
                        JobResponse jobResponse = new JobResponse();
                        jobResponse.setFields(job);
                        return jobResponse;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(jobResponses);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<?> getJobById(@PathVariable Integer jobId, @RequestHeader("Authorization") String token) {
        try {
            Integer posterId = jwtServices.extractIdFromHeader(token);
            Job job = jobPosterService.getJobByIdAndPosterId(jobId, posterId);
            JobResponse jobResponse = new JobResponse();
            jobResponse.setFields(job);
            return ResponseEntity.ok(jobResponse);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    @PostMapping("/jobs")
    public ResponseEntity<?> postMethodName(@Valid @RequestBody JobRequest entity,
            @RequestHeader("Authorization") String token) {
        try {
            Integer id = jwtServices.extractIdFromHeader(token);
            Job job = jobPosterService.createJob(entity, id);
            JobResponse jobResponse = new JobResponse();
            jobResponse.setFields(job);
            return ResponseEntity.status(HttpStatus.CREATED).body(jobResponse);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<?> updateJobById(@PathVariable Integer jobId, @Valid @RequestBody JobRequest entity,
            @RequestHeader("Authorization") String token) {
        try {
            Integer posterId = jwtServices.extractIdFromHeader(token);
            Job updatedJob = jobPosterService.updateJobByIdAndPosterId(jobId, entity, posterId);
            JobResponse jobResponse = new JobResponse();
            jobResponse.setFields(updatedJob);
            return ResponseEntity.ok(jobResponse);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<?> deleteJobById(@PathVariable Integer jobId,
            @RequestHeader("Authorization") String token) {
        try {
            Integer posterId = jwtServices.extractIdFromHeader(token);
            jobPosterService.deleteJobByIdAndPosterId(jobId, posterId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<?> getAllApplications(@PathVariable Integer jobId,
            @RequestHeader("Authorization") String token) {
        try {
            Integer posterId = jwtServices.extractIdFromHeader(token);
            List<Application> applications = applicationService.getAllApplications(jobId, posterId);
            List<ApplicationResponse> applicationResponses = applications.stream()
                    .map(application -> {
                        ApplicationResponse applicationResponse = new ApplicationResponse();
                        JobResponse jobResponse = new JobResponse();
                        jobResponse.setFields(application.getJob());
                        FreelancerResponse freelancerResponse = new FreelancerResponse();
                        freelancerResponse.setFields(application.getFreelancer(),
                                application.getFreelancer().getUser());
                        applicationResponse.setFields(application, jobResponse, freelancerResponse);
                        return applicationResponse;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(applicationResponses);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    @PatchMapping("/jobs/{jobId}/applications/{applicationId}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Integer jobId,
            @PathVariable Integer applicationId,
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UpdateApplicationStatusRequest updateRequest) {
        try {
            Integer posterId = jwtServices.extractIdFromHeader(token);
            applicationService.updateApplicationStatus(updateRequest.getNewStatus(),
                    applicationId, jobId,
                    posterId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    private ResponseEntity<?> handleException(IllegalArgumentException e) {
        HttpStatus status = e.getMessage().contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ErrorResponse(e.getMessage()));
    }



    @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getFreelancerByApplicationId(@PathVariable Integer applicationId) {
        try {
            FreelancerResponse freelancerResponse = jobPosterService.getFreelancerByApplicationId(applicationId);
            return ResponseEntity.ok(freelancerResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    
}
