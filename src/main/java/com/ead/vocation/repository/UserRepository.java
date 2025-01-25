package com.ead.vocation.repository;

import com.ead.vocation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

}
