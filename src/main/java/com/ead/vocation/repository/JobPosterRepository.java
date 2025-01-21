package com.ead.vocation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.JobPoster;

@Repository
public interface JobPosterRepository extends JpaRepository<JobPoster, Integer> {
    Optional<JobPoster> findByEmail(String email);
}
