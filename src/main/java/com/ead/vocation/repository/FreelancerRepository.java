package com.ead.vocation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.User;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Integer> {
 
    Optional<Freelancer> findByUser(User user);
}
