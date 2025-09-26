package com.apple.hub.AppleHub.controllers;

import com.apple.hub.AppleHub.dto.LoginRequest;
import com.apple.hub.AppleHub.dto.RegisterRequest;
import com.apple.hub.AppleHub.dto.UpdateUserProfileRequest;
import com.apple.hub.AppleHub.dto.UserResponse;
import com.apple.hub.AppleHub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = authService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/get-current-user-details/{userId}")
    public ResponseEntity<?> getUserDetailsById(@PathVariable UUID userId) {
        UserResponse user = authService.getCurrentUserDetails(userId);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-user-profile")
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest request) {
        UserResponse updatedUser = authService.updateUserDetails(request);
        if (updatedUser == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        return ResponseEntity.ok(updatedUser);
    }
}
