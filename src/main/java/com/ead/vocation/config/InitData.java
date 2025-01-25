package com.ead.vocation.config;

import com.ead.vocation.model.Admin;
import com.ead.vocation.model.User;
import com.ead.vocation.repository.AdminRepository;
import com.ead.vocation.repository.UserRepository;
import com.ead.vocation.service.AuthService;
import com.ead.vocation.shared.enums.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Value("${ADMIN_EMAIL:admin@admin.com}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:adminpassword123}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> existingAdmin = userRepository.findByEmail(adminEmail);
        if (existingAdmin.isEmpty()) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setPassword(adminPassword);
            user.setRole(Role.ADMIN);
            user.setName("Admin");
            User registeredUser = authService.register(user);

            Admin admin = new Admin();
            admin.setUser(registeredUser);
            adminRepository.save(admin);
            System.out.println("Default admin created with email: " + adminEmail);
        } else {
            System.out.println("Admin already exists: Skipping admin creation");
        }
    }
}
