package com.ummbatin.service_management.services;

import com.ummbatin.service_management.models.Role;
import com.ummbatin.service_management.models.User;
import com.ummbatin.service_management.repositories.RoleRepository;
import com.ummbatin.service_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // For setting createdAt
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(String email, String password, String roleName) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now()); // Set the creation timestamp
        return userRepository.save(user);
    }
}
