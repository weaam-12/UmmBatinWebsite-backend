package com.ummbatin.service_management.controllers;

import com.ummbatin.service_management.models.AuthenticationRequest;
import com.ummbatin.service_management.models.AuthenticationResponse;
import com.ummbatin.service_management.models.User;
import com.ummbatin.service_management.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    // ✅ Only admin can register users
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthenticationResponse registerUser(@RequestBody User user) {
        return authService.register(user);
    }

    // ✅ Anyone can login
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return authService.login(request);
    }
}
