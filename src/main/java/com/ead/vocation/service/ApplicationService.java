package com.ead.vocation.service;

import com.ead.vocation.model.Application;
import com.ead.vocation.model.Job;
import com.ead.vocation.repository.ApplicationRepository;
import com.ead.vocation.repository.JobRepository;
import com.ead.vocation.shared.enums.ApplicationStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    public Application getApplicationById(Integer applicationId, Integer jobId, Integer posterId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        if (!job.getJobPoster().getId().equals(posterId)) {
            throw new IllegalArgumentException("Poster ID does not match");
        }

        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
    }

    public List<Application> getAllApplications(Integer jobId, Integer posterId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        if (job.getJobPoster().getUser().getId() != posterId) {
            throw new IllegalArgumentException("Poster ID does not match");
        }

        return applicationRepository.findByJobId(jobId);
    }

    public void updateApplicationStatus(String newStatus, Integer applicationId, Integer jobId, Integer posterId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        if (job.getJobPoster().getUser().getId() != posterId) {
            throw new IllegalArgumentException("Poster ID does not match");
        }

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        ApplicationStatus status;
        try {
            status = ApplicationStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value");
        }

        application.setStatus(status);
        applicationRepository.save(application);
    }
}
