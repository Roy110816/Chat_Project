package com.example.chat_project.service;

import com.example.chat_project.entity.User;
import com.example.chat_project.exception.DuplicateResourceException;
import com.example.chat_project.exception.ResourceNotFoundException;
import com.example.chat_project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;



@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        // 检查用户名和邮箱是否已存在
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getActiveUsers(Pageable pageable) {
        return userRepository.findAllActiveUsers(pageable);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userDetails.getUsername() != null) {
                        if (!userDetails.getUsername().equals(user.getUsername())){
                            if (userRepository.existsByUsername(userDetails.getUsername())) {
                                throw new DuplicateResourceException("Username already exists");
                            }
                            user.setUsername(userDetails.getUsername());
                        }
                    }
                    if (userDetails.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    if (userDetails.getEmail() != null) {
                        user.setEmail(userDetails.getEmail());
                    }
                    if (userDetails.getAvatarUrl() != null) {
                        user.setAvatarUrl(userDetails.getAvatarUrl());
                    }
                    if (userDetails.getStatus() != null) {
                        user.setStatus(userDetails.getStatus());
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void recordLogin(Long userId) {
        userRepository.updateLastLogin(userId, LocalDateTime.now());
    }

    public Page<User> searchUsers(String username, String email, User.Status status, Pageable pageable) {
        return userRepository.searchUsers(username, email, status, pageable);
    }
}