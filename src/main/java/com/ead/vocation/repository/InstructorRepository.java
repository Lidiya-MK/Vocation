package com.ead.vocation.repository;

import com.ead.vocation.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Optional<Instructor> findByEmail(String email);
}
