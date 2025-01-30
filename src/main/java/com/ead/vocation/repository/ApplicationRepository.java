package com.ead.vocation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.Application;
import com.ead.vocation.model.Freelancer;
import com.ead.vocation.model.Job;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByFreelancer(Freelancer freelancer);

    List<Application> findByJobId(Integer jobId);

    Optional<Application> findByIdAndFreelancer(Integer id, Freelancer freelancer);
    boolean existsByJobAndFreelancer(Job job, Freelancer freelancer);


    void deleteByJob(Job job);
}
