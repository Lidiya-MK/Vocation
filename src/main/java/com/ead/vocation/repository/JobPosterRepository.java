package com.ead.vocation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.JobPoster;
import com.ead.vocation.model.User;

@Repository
public interface JobPosterRepository extends JpaRepository<JobPoster, Integer> {
    Optional<JobPoster> findByUser(User user);
}
