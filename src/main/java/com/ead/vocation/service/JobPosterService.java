package com.ead.vocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.vocation.model.JobPoster;
import com.ead.vocation.model.User;
import com.ead.vocation.repository.JobPosterRepository;
import com.ead.vocation.repository.UserRepository;

@Service
public class JobPosterService {
    @Autowired
    private JobPosterRepository jobPosterRepJobPosterRepository;

    @Autowired
    private UserRepository userRepository;

    public JobPoster createJobPoster(JobPoster jobPoster, Integer userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        jobPoster.setUser(user);
        return jobPosterRepJobPosterRepository.save(jobPoster);
    }
}
