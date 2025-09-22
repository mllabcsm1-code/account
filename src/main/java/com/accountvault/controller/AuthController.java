package com.accountvault.controller;

import com.accountvault.model.User;
import com.accountvault.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        if (userService.existsByUsername(user.getUsername())) {
            response.put("success", false);
            response.put("message", "Username already exists");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (userService.existsByEmail(user.getEmail())) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }
        
        User createdUser = userService.createUser(user);
        response.put("success", true);
        response.put("message", "User registered successfully");
        response.put("userId", createdUser.getUserId());
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            userOpt = userService.getUserByEmail(username);
        }
        
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }
        
        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            response.put("success", false);
            response.put("message", "Invalid password");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            response.put("success", false);
            response.put("message", "Account is not active");
            return ResponseEntity.badRequest().body(response);
        }
        
        response.put("success", true);
        response.put("message", "Login successful");
        response.put("user", user);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String username = request.get("username");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        
        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }
        
        User user = userOpt.get();
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            response.put("success", false);
            response.put("message", "Invalid old password");
            return ResponseEntity.badRequest().body(response);
        }
        
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userService.updateUser(user.getUserId(), user);
        
        response.put("success", true);
        response.put("message", "Password changed successfully");
        
        return ResponseEntity.ok(response);
    }
}
