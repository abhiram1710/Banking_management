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

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AppUser user) {

        try {

            Optional<AppUser> existingUser =
                    userRepository.findByUsername(user.getUsername());

            if(existingUser.isPresent()) {
                return ResponseEntity.badRequest()
                        .body("Username already exists");
            }

            user.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );

            user.setRole("ROLE_USER");

            userRepository.save(user);

            return ResponseEntity.ok("Registration Success");

        } catch(Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body("Registration Failed");
        }
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {

        try {

            Optional<AppUser> dbUser =
                    userRepository.findByUsername(user.getUsername());

            if(dbUser.isPresent()) {

                if(passwordEncoder.matches(
                        user.getPassword(),
                        dbUser.get().getPassword()
                )) {

                    return ResponseEntity.ok("Login Success");
                }
            }

            return ResponseEntity.status(401)
                    .body("Invalid Credentials");

        } catch(Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .body("Login Failed");
        }
    }
}