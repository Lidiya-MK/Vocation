package com.ead.vocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.Freelancer;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Integer> {
}
