package com.ead.vocation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

import com.ead.vocation.model.Job;
import com.ead.vocation.shared.enums.JobStatus;
import com.ead.vocation.shared.enums.JobType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

    @NotBlank(message = "Title is mandatory")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @NotNull(message = "Job type is mandatory")
    private JobType type;

    @NotEmpty(message = "At least one skill is required")
    private List<@NotBlank(message = "Skill cannot be blank") String> skillsRequired;

    @NotNull(message = "Budget is mandatory")
    @Positive(message = "Budget must be a positive number")
    private Double budget;

    @NotNull(message = "Start date is mandatory")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @NotNull(message = "Application deadline is mandatory")
    @Future(message = "Application deadline must be in the future")
    private LocalDate applicationDeadline;

    @NotNull(message = "Job status is mandatory")
    private JobStatus status;

    public Job toJobEntity() {
        Job job = new Job();
        job.setTitle(this.title);
        job.setDescription(this.description);
        job.setType(this.type);
        job.setSkillsRequired(this.skillsRequired);
        job.setBudget(this.budget);
        job.setStartDate(this.startDate);
        job.setEndDate(this.endDate);
        job.setApplicationDeadline(this.applicationDeadline);
        job.setStatus(this.status);
        return job;
    }
}
