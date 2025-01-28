package com.ead.vocation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobPosterRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 20, message = "Password should have 6 to 20 characters")
    private String password;

    private List<@Pattern(regexp = "^(https?://).+", message = "Each link must be a valid URL starting with http:// or https://") String> links;

    @NotBlank(message = "Industry is mandatory")
    private String industry;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Phone number should be valid")
    private String phoneNumber;
}
