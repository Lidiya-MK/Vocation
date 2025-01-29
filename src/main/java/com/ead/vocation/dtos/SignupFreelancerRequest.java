package com.ead.vocation.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupFreelancerRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 20, message = "Password should have 6 to 20 characters")
    private String password;

    @NotEmpty(message = "At least one skill must be provided")
    private List<String> skills;

    @NotBlank(message = "Industry is mandatory")
    private String industry;

    @Min(value = 0, message = "Years of experience cannot be negative")
    private Integer yearsOfExperience;

    @Pattern(regexp = "^(https?://).+", message = "Resume link must be a valid URL starting with http:// or https://")
    private String resumeLink;

    @Size(max = 500, message = "Cover letter should not exceed 500 characters")
    private String coverLetter;

    private List<@Pattern(regexp = "^(https?://).+", message = "Each link must be a valid URL starting with http:// or https://") String> links;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Phone number should only contain numbers and an optional leading '+'")
    private String phoneNumber;

    @Pattern(regexp = "^(https?://).+", message = "Profile picture link must be a valid URL starting with http:// or https://")
    private String profilePicture;
}
