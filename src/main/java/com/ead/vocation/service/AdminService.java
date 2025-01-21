package com.ead.vocation.service;

import com.ead.vocation.model.Admin;
import com.ead.vocation.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin registerAdmin(Admin admin) {

        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        return adminRepository.save(admin);
    }
}
