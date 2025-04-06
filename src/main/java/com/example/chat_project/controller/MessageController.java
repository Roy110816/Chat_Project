package com.example.chat_project.controller;

import com.example.chat_project.entity.Message;
import com.example.chat_project.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.createMessage(message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Message>> getAllMessages(Pageable pageable) {
        return ResponseEntity.ok(messageService.getAllMessages(pageable));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Message>> getMessagesByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(messageService.getMessagesByUser(userId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(
            @PathVariable Long id,
            @RequestBody Message messageDetails) {
        return ResponseEntity.ok(messageService.updateMessage(id, messageDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mark-read")
    public ResponseEntity<Void> markMessagesAsRead(@RequestBody List<Long> messageIds) {
        messageService.markMessagesAsRead(messageIds);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cleanup")
    public ResponseEntity<Integer> cleanupOldMessages(
            @RequestParam(defaultValue = "30") int daysToKeep) {
        return ResponseEntity.ok(messageService.cleanupOldMessages(daysToKeep));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Message>> searchMessages(
            @RequestParam(required = false) Long senderId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            Pageable pageable) {
        return ResponseEntity.ok(messageService.searchMessages(
                senderId, content, startDate, endDate, pageable
        ));
    }

    @GetMapping("/recent-active")
    public ResponseEntity<List<Message>> getRecentActiveMessages() {
        return ResponseEntity.ok(messageService.getRecentActiveMessages());
    }
}