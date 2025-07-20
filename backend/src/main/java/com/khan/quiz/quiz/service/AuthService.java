package com.khan.quiz.quiz.service;

import com.khan.quiz.quiz.dto.*;
import com.khan.quiz.quiz.model.User;
import com.khan.quiz.quiz.respository.UserRepository;
import com.khan.quiz.quiz.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;   // Final fields for dependency injection
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public void register(RegisterRequest request, HttpServletResponse response) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // Build and save user
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender() != null ? request.getGender() : "M")
                .userType("user")
                .isActive(true)
                .emailVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(newUser);

        // Authenticate immediately after register
        String jwt = jwtUtil.generateToken(newUser.getEmail());

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Allow over HTTP during local development
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 day
        response.addCookie(cookie);
    }

    public void login(LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Allow over HTTP during local development
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 day


        response.addCookie(cookie);
    }
}
