package com.bank.controller;

import com.bank.entity.AppUser;
import com.bank.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthApiController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ LOGIN API
    @PostMapping("/login")
    public String login(@RequestBody AppUser loginUser) {

        Optional<AppUser> user = userRepository.findByUsername(loginUser.getUsername());

        if (user.isPresent()) {
            if (passwordEncoder.matches(loginUser.getPassword(), user.get().getPassword())) {
                return "Login Success";
            }
        }

        return "Invalid Credentials";
    }
}