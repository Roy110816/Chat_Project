package com.example.chat_project.repository;

import com.example.chat_project.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findBySenderId(Long senderId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE " +
            "(:senderId IS NULL OR m.sender.id = :senderId) AND " +
            "(:content IS NULL OR m.content LIKE %:content%) AND " +
            "(:startDate IS NULL OR m.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR m.createdAt <= :endDate)")
    Page<Message> searchMessages(
            @Param("senderId") Long senderId,
            @Param("content") String content,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE m.id IN :messageIds")
    int markMessagesAsRead(@Param("messageIds") List<Long> messageIds);

    @Transactional
    @Modifying
    @Query("DELETE FROM Message m WHERE m.createdAt < :date")
    int deleteMessagesOlderThan(@Param("date") LocalDateTime date);

    @Query(value = """
        SELECT m.* FROM messages m
        JOIN users u ON m.sender_id = u.id
        WHERE u.status = 'ACTIVE'
        ORDER BY m.created_at DESC
        LIMIT 100""", nativeQuery = true)
    List<Message> findRecentActiveMessages();
}