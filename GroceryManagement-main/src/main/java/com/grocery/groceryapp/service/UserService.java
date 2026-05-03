package com.grocery.groceryapp.service;

import com.grocery.groceryapp.dto.AuthRequest;
import com.grocery.groceryapp.dto.AuthResponse;
import com.grocery.groceryapp.model.User;
import com.grocery.groceryapp.repository.UserRepository;
import com.grocery.groceryapp.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // =========================
    // 🧑‍💼 REGISTER
    // =========================
    public String register(User user) {

        // check duplicate user
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        // encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // default role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        userRepository.save(user);

        return "User registered successfully";
    }

    // =========================
    // 🔐 LOGIN
    // =========================
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // password check
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 🔥 FIX: include ROLE in token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole(),
                "Login successful"
        );
    }
}