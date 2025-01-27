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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ead.vocation.dtos.JobRequest;
import com.ead.vocation.dtos.JobResponse;
import com.ead.vocation.model.Application;
import com.ead.vocation.model.Job;
import com.ead.vocation.service.ApplicationService;
import com.ead.vocation.service.JobPosterService;
import com.ead.vocation.shared.ErrorResponse;
import com.ead.vocation.shared.util.JwtServices;

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
    public String getDashboard(@RequestHeader("Authorization") String token, Model model) {
        Integer id = jwtServices.extractIdFromHeader(token);
        model.addAttribute("id", id);
        return "test-page";
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

    // TODO: create output DTO for this endpoint
    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<?> getAllApplications(@PathVariable Integer jobId,
            @RequestHeader("Authorization") String token) {
        try {
            Integer posterId = jwtServices.extractIdFromHeader(token);
            List<Application> applications = applicationService.getAllApplications(jobId, posterId);
            return ResponseEntity.ok(applications);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    // TODO: create output DTO for this endpoint
    @GetMapping("/jobs/{jobId}/applications/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable Integer jobId, @PathVariable Integer applicationId,
            @RequestHeader("Authorization") String token) {
        try {
            Integer posterId = jwtServices.extractIdFromHeader(token);
            Application application = applicationService.getApplicationById(applicationId, jobId,
                    posterId);
            return ResponseEntity.ok(application);
        } catch (IllegalArgumentException e) {
            return handleException(e);
        }
    }

    private ResponseEntity<?> handleException(IllegalArgumentException e) {
        HttpStatus status = e.getMessage().contains("not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ErrorResponse(e.getMessage()));
    }
}
