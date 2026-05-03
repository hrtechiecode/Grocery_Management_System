package com.grocery.groceryapp.controller;

import com.grocery.groceryapp.dto.AuthRequest;
import com.grocery.groceryapp.dto.AuthResponse;
import com.grocery.groceryapp.model.User;
import com.grocery.groceryapp.repository.UserRepository;
import com.grocery.groceryapp.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, null, "User already exists"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return ResponseEntity.ok(
                new AuthResponse(null, user.getEmail(), user.getRole(), "User registered successfully")
        );
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401)
                    .body(new AuthResponse(null, null, null, "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(
                new AuthResponse(token, user.getEmail(), user.getRole(), "Login successful")
        );
    }

    // (optional)
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}