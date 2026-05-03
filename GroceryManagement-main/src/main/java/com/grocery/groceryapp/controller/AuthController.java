package com.grocery.groceryapp.controller;

import com.grocery.groceryapp.model.User;
import com.grocery.groceryapp.repository.UserRepository;
import com.grocery.groceryapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ================= LOGIN =================
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        String email = user.getEmail();
        String password = user.getPassword();

        User dbUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getRole());

        Map<String, String> res = new HashMap<>();
        res.put("token", token);
        res.put("role", dbUser.getRole());
        res.put("email", dbUser.getEmail());

        return res;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {

        Map<String, String> res = new HashMap<>();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            res.put("message", "Email already exists");
            return res;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        res.put("message", "User registered successfully");
        return res;
    }
}