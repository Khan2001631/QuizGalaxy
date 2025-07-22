package com.khan.quiz.quiz.controller;

import com.khan.quiz.quiz.dto.*;
import com.khan.quiz.quiz.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

   private final AuthService authService; // @RequiredArgsConstructor creates constructor injection

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        authService.register(request, response);
        return ResponseEntity.ok(
                ApiResponse.success(200, "User registered successfully.")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        authService.login(request, response);

        return ResponseEntity.ok(
                ApiResponse.success(200, "User logged-in successfully.")
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Invalidate the cookie by setting maxAge to 0
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false) // Set to true in production (requires HTTPS)
                .path("/")
                .sameSite("Lax")
                .maxAge(0) // <--- This removes the cookie
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(
                ApiResponse.success(200, "Logged out successfully")
        );
    }

}
