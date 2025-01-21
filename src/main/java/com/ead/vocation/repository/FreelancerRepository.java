package com.ead.vocation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.Freelancer;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Integer> {
    Optional<Freelancer> findByEmail(String email);
}
