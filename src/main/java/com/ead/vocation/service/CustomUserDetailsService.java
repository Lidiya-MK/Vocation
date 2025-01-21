package com.ead.vocation.service;

import com.ead.vocation.model.Admin;
import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.JobPoster;
import com.ead.vocation.repository.AdminRepository;
import com.ead.vocation.repository.FreelancerRepository;
import com.ead.vocation.repository.JobPosterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private FreelancerRepository freelancerRepository;
    @Autowired
    private JobPosterRepository jobPosterRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Freelancer> freelancer = freelancerRepository.findByEmail(email);
        if (freelancer.isPresent()) {
            return User.builder()
                    .username(freelancer.get().getEmail())
                    .password(freelancer.get().getPassword())
                    .roles("FREELANCER")
                    .build();
        }

        Optional<JobPoster> jobPoster = jobPosterRepository.findByEmail(email);
        if (jobPoster.isPresent()) {
            return User.builder()
                    .username(jobPoster.get().getEmail())
                    .password(jobPoster.get().getPassword())
                    .roles("JOBPOSTER")
                    .build();
        }

        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return User.builder()
                    .username(admin.get().getEmail())
                    .password(admin.get().getPassword())
                    .roles("ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}