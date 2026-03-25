package com.bank.controller;

import com.bank.entity.AppUser;
import com.bank.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 🔥 ROLE BASED DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {

        String role = authentication.getAuthorities()
                                    .iterator()
                                    .next()
                                    .getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return "admin-dashboard";
        } else {
            return "user-dashboard";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password) {

        AppUser user = new AppUser(
                username,
                passwordEncoder.encode(password),
                "ROLE_USER"
        );

        userRepository.save(user);

        return "redirect:/login";
    }
}