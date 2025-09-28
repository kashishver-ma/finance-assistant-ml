package com.financeAssitant.FinBrains.service;

import com.financeAssitant.FinBrains.dto.AuthResponse;
import com.financeAssitant.FinBrains.dto.LoginRequest;
import com.financeAssitant.FinBrains.dto.SignupRequest;
import com.financeAssitant.FinBrains.entity.User;
import com.financeAssitant.FinBrains.repository.UserRepository;
import com.financeAssitant.FinBrains.utility.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    public AuthResponse signup(SignupRequest signupRequest) {
        // Check if user already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // Create new user
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        // Set profile information
        User.Profile profile = new User.Profile();
        profile.setFirstName(signupRequest.getFirstName());
        profile.setLastName(signupRequest.getLastName());
        profile.setPhone(signupRequest.getPhone());
        profile.setDateOfBirth(signupRequest.getDateOfBirth());
        profile.setOccupation(signupRequest.getOccupation());
        profile.setMonthlyIncome(signupRequest.getMonthlyIncome());
        user.setProfile(profile);

        // Generate email verification token
//        String verificationToken = UUID.randomUUID().toString();
//        user.getAuthentication().setEmailVerificationToken(verificationToken);

        // Save user
        User savedUser = userRepository.save(user);

        // Send verification email
//        emailService.sendVerificationEmail(user.getEmail(),
//                user.getProfile().getFirstName(), verificationToken);

        // Generate JWT token
        String jwt = jwtUtils.generateJwtToken(savedUser.getId(), savedUser.getEmail());

        return AuthResponse.builder()
                .token(jwt)
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getProfile().getFirstName())
                .lastName(savedUser.getProfile().getLastName())
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        // Update last login
        user.getAuthentication().setLastLogin(LocalDateTime.now());
        user.getMetadata().setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT token
        String jwt = jwtUtils.generateJwtToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .token(jwt)
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getProfile().getFirstName())
                .lastName(user.getProfile().getLastName())
                .build();
    }

    public User getCurrentUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public boolean verifyEmail(String token) {
        Optional<User> userOptional = userRepository.findByAuthentication_EmailVerificationToken(token);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        user.getAuthentication().setEmailVerified(true);
        user.getAuthentication().setEmailVerificationToken(null);
        user.getMetadata().setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
        return true;
    }
}
