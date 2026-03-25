package com.bank.config;

import com.bank.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            .authorizeHttpRequests(auth -> auth

                // Public pages
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // Account APIs
                .requestMatchers(HttpMethod.POST, "/accounts/**").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.GET, "/accounts/**").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.PUT, "/accounts/**").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.DELETE, "/accounts/**").hasAnyRole("ADMIN","USER")

                .anyRequest().authenticated()
            )

            // Login page
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )

            // Basic auth for Postman
            .httpBasic(basic -> {})

            // Logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}