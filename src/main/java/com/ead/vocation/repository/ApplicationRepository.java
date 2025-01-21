package com.ead.vocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ead.vocation.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
}
