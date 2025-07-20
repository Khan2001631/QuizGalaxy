package com.khan.quiz.quiz.utils;

import com.khan.quiz.quiz.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String jwt = null;
//        String username = null;
//
//        // Get JWT from cookie named "jwt"
//        if (request.getCookies() != null) {
//            for (var cookie : request.getCookies()) {
//                if (cookie.getName().equals("jwt")) {
//                    jwt = cookie.getValue();
//                    System.out.println("[JwtAuthFilter] JWT found in cookie: " + jwt);
//                    break;
//                }
//                else {
//                    System.out.println("[JwtAuthFilter] No cookies found in request.");
//                }
//            }
//        }
//
//        // Extract username if JWT is found
//        if (jwt != null) {
//            username = jwtUtil.extractUsername(jwt);
//            System.out.println("[JwtAuthFilter] No cookies found in request.");
//        }
//
//        // Authenticate if user is not already authenticated
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            System.out.println("[JwtAuthFilter] JWT is valid. Setting authentication in context.");
//
//            if (jwtUtil.validateToken(jwt)) {
//                UsernamePasswordAuthenticationToken token =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(token);
//            }
//        }
//        System.out.println("End log");
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // ✅ 1. Extract token from cookie instead of header
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        // ✅ 2. Proceed if token is present
        if (jwt != null) {
            String username = jwtUtil.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}

