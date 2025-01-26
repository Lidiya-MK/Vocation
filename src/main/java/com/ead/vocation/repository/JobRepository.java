package com.ead.vocation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.Job;
import com.ead.vocation.model.JobPoster;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByJobPoster(JobPoster jobPoster);

    Optional<Job> findByIdAndJobPoster(Integer id, JobPoster jobPoster);
}
