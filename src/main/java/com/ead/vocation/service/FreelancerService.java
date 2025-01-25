package com.ead.vocation.service;

import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.User;
import com.ead.vocation.repository.FreelancerRepository;
import com.ead.vocation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private UserRepository userRepository;

    public Freelancer createFreelancer(Freelancer freelancer, Integer userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        freelancer.setUser(user);
        return freelancerRepository.save(freelancer);
    }
}
