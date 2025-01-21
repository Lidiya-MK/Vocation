package com.ead.vocation.repository;

import com.ead.vocation.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUsername(String username);

    Optional<Student> findByEmail(String email);
}