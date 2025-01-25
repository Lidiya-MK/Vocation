package com.ead.vocation.dtos;

import java.util.List;

import com.ead.vocation.model.JobPoster;
import com.ead.vocation.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPosterResponse {
    private Integer id;
    private String name;
    private String email;
    private String industry;
    private List<String> links;
    private String description;
    private String location;
    private String phoneNumber;
    private String profilePicture;

    public void setFields(JobPoster jobPoster, User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.industry = jobPoster.getIndustry();
        this.links = jobPoster.getLinks();
        this.description = jobPoster.getDescription();
        this.location = jobPoster.getLocation();
        this.phoneNumber = jobPoster.getPhoneNumber();
        this.profilePicture = jobPoster.getProfilePicture();
    }
}
