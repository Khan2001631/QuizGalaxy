package com.khan.quiz.quiz.controller;

import com.khan.quiz.quiz.dto.*;
import com.khan.quiz.quiz.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    private String createHttpOnlyCookie(String jwt) {
        return ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true) // Set to false for localhost during dev if not using HTTPS
                .path("/")
                .maxAge(24 * 60 * 60) // 1 day
                .sameSite("Strict") // or "Lax" depending on your use case
                .build()
                .toString();
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String jwt = authService.register(request); // return JWT directly from service
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, createHttpOnlyCookie(jwt))
                .body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String jwt = authService.login(request); // return JWT directly from service
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, createHttpOnlyCookie(jwt))
                .body("Login successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Invalidate the cookie by setting maxAge to 0
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true) // Set to true in production (requires HTTPS)
                .path("/")
                .sameSite("Lax")
                .maxAge(0) // <--- This removes the cookie
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Logged out successfully");
    }

}
