package com.ead.vocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.JobPoster;

@Repository
public interface JobPosterRepository extends JpaRepository<JobPoster, Integer> {
}
