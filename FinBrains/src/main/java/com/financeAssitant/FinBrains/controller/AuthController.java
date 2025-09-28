package com.financeAssitant.FinBrains.controller;

import com.financeAssitant.FinBrains.dto.AuthResponse;
import com.financeAssitant.FinBrains.dto.LoginRequest;
import com.financeAssitant.FinBrains.dto.SignupRequest;
import com.financeAssitant.FinBrains.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {

        try {
            AuthResponse authResponse = userService.signup(signupRequest);
            authResponse.setNewUser(true);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Account created successfully! Please check your email to verify your account.");
            response.put("data", authResponse);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = userService.login(loginRequest);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful!");
            response.put("data", authResponse);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.badRequest().body(response);
        }
    }

    //will do this later (for now simple login)
    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        Map<String, Object> response = new HashMap<>();

        if (userService.verifyEmail(token)) {
            response.put("success", true);
            response.put("message", "Email verified successfully!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid or expired verification token!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            // Extract token from "Bearer <token>"
            String jwt = token.substring(7);
            // Implementation depends on your JWT filter setup

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User data retrieved successfully!");
            // Add user data here

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Unauthorized!");

            return ResponseEntity.status(401).body(response);
        }
    }
}
