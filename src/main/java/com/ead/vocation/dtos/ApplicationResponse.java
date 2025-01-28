package com.ead.vocation.dtos;

import com.ead.vocation.model.Application;
import com.ead.vocation.shared.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {

    private Integer id;
    private JobResponse job;
    private FreelancerResponse freelancer;
    private ApplicationStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setFields(Application application, JobResponse jobResponse, FreelancerResponse freelancerResponse) {
        this.id = application.getId();
        this.job = jobResponse;
        this.freelancer = freelancerResponse;
        this.status = application.getStatus();
        this.submittedAt = application.getSubmittedAt();
        this.createdAt = application.getCreatedAt();
        this.updatedAt = application.getUpdatedAt();
    }
}
