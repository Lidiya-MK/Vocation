package com.ead.vocation.service;

import com.ead.vocation.model.User;
import com.ead.vocation.repository.UserRepository;
import com.ead.vocation.shared.enums.Role;
import com.ead.vocation.shared.util.JwtServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtServices jwtServices;

    public String login(String email, String password, Role role) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (user.getRole() != role) {
            throw new IllegalArgumentException("User with role not found");
        }

        return jwtServices.generateToken(String.valueOf(user.getId()), user.getRole());
    }

    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
