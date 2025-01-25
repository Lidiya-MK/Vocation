package com.ead.vocation.dtos;

import java.util.List;

import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreelancerResponse {
    private Integer id;
    private String name;
    private String email;
    private List<String> skills;
    private String industry;
    private Integer yearsOfExperience;
    private String resumeLink;
    private String coverLetter;
    private List<String> links;
    private String description;
    private String location;
    private String phoneNumber;
    private String profilePicture;

    public void setFields(Freelancer freelancer, User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.skills = freelancer.getSkills();
        this.industry = freelancer.getIndustry();
        this.yearsOfExperience = freelancer.getYearsOfExperience();
        this.resumeLink = freelancer.getResumeLink();
        this.coverLetter = freelancer.getCoverLetter();
        this.links = freelancer.getLinks();
        this.description = freelancer.getDescription();
        this.location = freelancer.getLocation();
        this.phoneNumber = freelancer.getPhoneNumber();
        this.profilePicture = freelancer.getProfilePicture();
    }
}
