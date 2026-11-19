package com.kevin.fincore.user.controller;

import com.kevin.fincore.user.dto.CreateUserRequest;
import com.kevin.fincore.user.dto.UserResponse;
import com.kevin.fincore.user.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users") // Anotação que define a classe como um controlador REST e mapeia as requisições para o caminho "/api/users"
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
