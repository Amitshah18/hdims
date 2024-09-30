package com.hdims.hdims.service;

import com.hdims.hdims.model.Role;
import com.hdims.hdims.model.User;
import com.hdims.hdims.repository.RoleRepository;
import com.hdims.hdims.repository.UserRepository;
import com.hdims.hdims.security.JwtUtils;
import com.hdims.hdims.dto.LoginRequest;
import com.hdims.hdims.dto.LoginResponse;
import com.hdims.hdims.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public User registerUser(User user, Set<String> strRoles) {
        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign roles
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName("HEALTH_WORKER")
                    .orElseThrow(() -> new RuntimeException("Role not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role fetchedRole = roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Role not found."));
                roles.add(fetchedRole);
            });
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginRequest.getEmail()));
        
        // Check password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        // Generate JWT
        String token = jwtUtils.generateJwtToken(user);

        return new LoginResponse(token);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
