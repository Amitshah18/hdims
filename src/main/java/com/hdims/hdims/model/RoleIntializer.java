package com.hdims.hdims.service;

import com.hdims.hdims.model.Role;
import com.hdims.hdims.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RoleInitializer {
    
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        String[] roles = {"ADMIN", "STATE_ADMIN", "DISTRICT_ADMIN", "FACILITY_ADMIN", "HEALTH_WORKER"};
        for (String roleName : roles) {
            if (!roleRepository.findByName(roleName).isPresent()) {
                Role role = Role.builder()
                        .name(roleName)
                        .build();
                roleRepository.save(role);
            }
        }
    }
}
