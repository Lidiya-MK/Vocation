package com.ead.vocation.dtos;

import com.ead.vocation.model.Application;
import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.Job;
import com.ead.vocation.shared.enums.ApplicationStatus;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    @NotNull(message = "Job ID is mandatory")
    private Integer jobId;

    @NotNull(message = "Freelancer ID is mandatory")
    private Integer freelancerId;

    @NotNull(message = "Application status is mandatory")
    private ApplicationStatus status;

    public Application toApplicationEntity(Job job, Freelancer freelancer) {
        Application application = new Application();
        application.setJob(job);
        application.setFreelancer(freelancer);
        application.setStatus(this.status);
        application.setSubmittedAt(LocalDateTime.now());
        return application;
    }
}
