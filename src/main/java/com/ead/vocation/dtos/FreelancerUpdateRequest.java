package com.ead.vocation.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FreelancerUpdateRequest {
    private String name; 
    private String email; 
    private String industry;
    private Integer yearsOfExperience;
    private String resumeLink;
    private String coverLetter;
    private List<String> skills;
    private List<String> links;
    private String description;
    private String location;
    private String phoneNumber;
}
