package com.ummbatin.service_management.controllers;

import com.ummbatin.service_management.dtos.UserDto;
import com.ummbatin.service_management.models.User;
import com.ummbatin.service_management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')") //  Only admins can see all users
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserDto::new)
                .toList();
    }

    @GetMapping("/profile")
    public UserDto getUserProfile(Authentication authentication) {
        String email = authentication.getName();

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDto(user);
    }
}
