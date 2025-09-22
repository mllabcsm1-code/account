package com.accountvault.service;

import com.accountvault.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    Page<User> getAllUsers(Pageable pageable);
    Page<User> getUsersByRole(User.UserRole role, Pageable pageable);
    Page<User> getUsersByStatus(User.UserStatus status, Pageable pageable);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User updateReputationScore(Long userId, BigDecimal newScore);
    User updateUserStatus(Long userId, User.UserStatus status);
}
