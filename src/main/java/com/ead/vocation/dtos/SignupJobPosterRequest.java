package com.ead.vocation.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupJobPosterRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name should not exceed 50 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 20, message = "Password should have 6 to 20 characters")
    private String password;

    @NotBlank(message = "Industry is mandatory")
    private String industry;

    private List<@Pattern(regexp = "^(https?://).+", message = "Each link must be a valid URL starting with http:// or https://") String> links;

    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Pattern(regexp = "^\\+?[0-9]*$", message = "Phone number should only contain numbers and an optional leading '+'")
    private String phoneNumber;

    @Pattern(regexp = "^(https?://).+", message = "Profile picture link must be a valid URL starting with http:// or https://")
    private String profilePicture;
}
