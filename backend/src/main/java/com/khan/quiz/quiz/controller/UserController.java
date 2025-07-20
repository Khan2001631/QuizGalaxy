package com.khan.quiz.quiz.controller;

import com.khan.quiz.quiz.dto.UserDTO;
import com.khan.quiz.quiz.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO userDTO = userService.getCurrentUser();
        return ResponseEntity.ok(userDTO);
    }
}
