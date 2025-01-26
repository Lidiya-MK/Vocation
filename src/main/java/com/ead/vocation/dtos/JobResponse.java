package com.ead.vocation.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ead.vocation.model.Job;
import com.ead.vocation.shared.enums.JobStatus;
import com.ead.vocation.shared.enums.JobType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private Integer id;
    private JobPosterResponse jobPoster;
    private String title;
    private String description;
    private JobType type;
    private List<String> skillsRequired;
    private Double budget;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate applicationDeadline;
    private JobStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setFields(Job job) {
        this.id = job.getId();
        this.jobPoster = new JobPosterResponse();
        this.jobPoster.setFields(job.getJobPoster(), job.getJobPoster().getUser());
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.type = job.getType();
        this.skillsRequired = job.getSkillsRequired();
        this.budget = job.getBudget();
        this.startDate = job.getStartDate();
        this.endDate = job.getEndDate();
        this.applicationDeadline = job.getApplicationDeadline();
        this.status = job.getStatus();
        this.createdAt = job.getCreatedAt();
        this.updatedAt = job.getUpdatedAt();
    }
}
