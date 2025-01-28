package com.ead.vocation.service;

import com.ead.vocation.dtos.JobResponse;
import com.ead.vocation.model.Job;
import com.ead.vocation.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository; 

    public List<JobResponse> getAllJobs() {
        List<Job> jobs = jobRepository.findAll(); 

        
        return jobs.stream()
                .map(job -> {
                    JobResponse jobResponse = new JobResponse();
                    jobResponse.setFields(job);
                    return jobResponse;
                })
                .collect(Collectors.toList());
    }
}
