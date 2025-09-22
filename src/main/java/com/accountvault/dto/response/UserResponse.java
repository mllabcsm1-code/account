package com.accountvault.dto.response;

import com.accountvault.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private User.UserRole role;
    private BigDecimal reputationScore;
    private String verificationLevel;
    private User.UserStatus status;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponse fromUser(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setReputationScore(user.getReputationScore());
        response.setVerificationLevel(user.getVerificationLevel());
        response.setStatus(user.getStatus());
        response.setLastLogin(user.getLastLogin());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
