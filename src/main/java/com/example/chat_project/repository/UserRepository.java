package com.example.chat_project.repository;

import com.example.chat_project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.status = 'ACTIVE'")
    Page<User> findAllActiveUsers(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :time WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId, @Param("time") LocalDateTime time);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :userId")
    int updateUserStatus(@Param("userId") Long userId, @Param("status") User.Status status);

    @Query("SELECT u FROM User u WHERE " +
            "(:username IS NULL OR u.username LIKE %:username%) AND " +
            "(:email IS NULL OR u.email LIKE %:email%) AND " +
            "(:status IS NULL OR u.status = :status)")
    Page<User> searchUsers(
            @Param("username") String username,
            @Param("email") String email,
            @Param("status") User.Status status,
            Pageable pageable
    );
}