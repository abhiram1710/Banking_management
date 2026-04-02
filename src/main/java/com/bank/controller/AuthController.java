package com.bank.controller;

import com.bank.entity.AppUser;
import com.bank.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ REGISTER API
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return ResponseEntity.ok("User Registered Successfully");
    }

    // ✅ LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {

        Optional<AppUser> dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser.isPresent()) {
            AppUser existingUser = dbUser.get();

            if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.ok("Login Success");
            }
        }

        return ResponseEntity.status(401).body("Invalid Username or Password");
    }
}