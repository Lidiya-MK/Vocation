package com.ead.vocation.service;

import com.ead.vocation.dtos.ApplicationRequest;
import com.ead.vocation.dtos.ApplicationResponse;
import com.ead.vocation.dtos.FreelancerResponse;
import com.ead.vocation.dtos.FreelancerUpdateRequest;
import com.ead.vocation.dtos.JobResponse;
import com.ead.vocation.model.Application;
import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.Job;
import com.ead.vocation.model.User;
import com.ead.vocation.repository.ApplicationRepository;
import com.ead.vocation.repository.FreelancerRepository;
import com.ead.vocation.repository.JobRepository;
import com.ead.vocation.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public Freelancer createFreelancer(Freelancer freelancer, Integer userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        freelancer.setUser(user);
        return freelancerRepository.save(freelancer);
    }

    public FreelancerResponse getFreelancerByUserId(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Freelancer freelancer = freelancerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        FreelancerResponse response = new FreelancerResponse();
        response.setFields(freelancer, user);

        return response;
    }

    public Application createApplication(ApplicationRequest applicationRequest, Integer freelancerID) {

        User user = userRepository.findById(freelancerID)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Freelancer freelancer = freelancerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        Job job = jobRepository.findById(applicationRequest.getJobId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));

        boolean alreadyApplied = applicationRepository.existsByJobAndFreelancer(job, freelancer);
        if (alreadyApplied) {
            throw new RuntimeException("You have already applied for this job.");
        }

        Application applicationEntity = applicationRequest.toApplicationEntity(job, freelancer);

        return applicationRepository.save(applicationEntity);
    }

    public List<ApplicationResponse> getAllApplicationsByFreelancerId(Integer freelancerID) {

        User user = userRepository.findById(freelancerID)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        Freelancer freelancer = freelancerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        if (freelancer == null) {
            throw new IllegalArgumentException("Freelancer not found");
        }

        List<Application> applications = applicationRepository.findByFreelancer(freelancer);

        List<ApplicationResponse> applicationResponses = applications.stream()
                .map(application -> {
                    JobResponse jobResponse = new JobResponse();
                    jobResponse.setFields(application.getJob());

                    Freelancer freelancerData = application.getFreelancer();
                    User userData = freelancerData.getUser();

                    FreelancerResponse freelancerResponse = new FreelancerResponse();
                    freelancerResponse.setFields(freelancerData, userData);

                    ApplicationResponse applicationResponse = new ApplicationResponse();
                    applicationResponse.setFields(application, jobResponse, freelancerResponse);

                    return applicationResponse;
                })
                .collect(Collectors.toList());

        return applicationResponses;
    }

    public void deleteApplicationByIdAndFreelancerId(Integer applicationID, Integer freelancerID) {

        User user = userRepository.findById(freelancerID)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        Freelancer freelancer = freelancerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        Application application = applicationRepository.findByIdAndFreelancer(applicationID, freelancer)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Application not found or does not belong to the specified freelancer"));

        applicationRepository.delete(application);
    }

    public FreelancerResponse getFreelancerProfile(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Freelancer freelancer = freelancerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        FreelancerResponse freelancerResponse = new FreelancerResponse();
        freelancerResponse.setFields(freelancer, user);

        return freelancerResponse;
    }

    public FreelancerResponse updateFreelancerProfile(Integer userId, FreelancerUpdateRequest freelancerUpdateRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Freelancer freelancer = freelancerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Freelancer not found"));

        if (freelancerUpdateRequest.getName() != null) {
            user.setName(freelancerUpdateRequest.getName());
        }
        if (freelancerUpdateRequest.getEmail() != null) {
            user.setEmail(freelancerUpdateRequest.getEmail());
        }

        userRepository.save(user);

        if (freelancerUpdateRequest.getIndustry() != null) {
            freelancer.setIndustry(freelancerUpdateRequest.getIndustry());
        }
        if (freelancerUpdateRequest.getYearsOfExperience() != null) {
            freelancer.setYearsOfExperience(freelancerUpdateRequest.getYearsOfExperience());
        }
        if (freelancerUpdateRequest.getResumeLink() != null) {
            freelancer.setResumeLink(freelancerUpdateRequest.getResumeLink());
        }
        if (freelancerUpdateRequest.getCoverLetter() != null) {
            freelancer.setCoverLetter(freelancerUpdateRequest.getCoverLetter());
        }
        if (freelancerUpdateRequest.getSkills() != null) {
            freelancer.setSkills(freelancerUpdateRequest.getSkills());
        }
        if (freelancerUpdateRequest.getLinks() != null) {
            freelancer.setLinks(freelancerUpdateRequest.getLinks());
        }
        if (freelancerUpdateRequest.getDescription() != null) {
            freelancer.setDescription(freelancerUpdateRequest.getDescription());
        }
        if (freelancerUpdateRequest.getLocation() != null) {
            freelancer.setLocation(freelancerUpdateRequest.getLocation());
        }
        if (freelancerUpdateRequest.getPhoneNumber() != null) {
            freelancer.setPhoneNumber(freelancerUpdateRequest.getPhoneNumber());
        }

        freelancerRepository.save(freelancer);

        FreelancerResponse response = new FreelancerResponse();
        response.setFields(freelancer, user);
        return response;
    }

}
