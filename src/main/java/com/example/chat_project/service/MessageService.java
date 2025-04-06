package com.example.chat_project.service;

import com.example.chat_project.entity.Message;
import com.example.chat_project.entity.User;
import com.example.chat_project.exception.ResourceNotFoundException;
import com.example.chat_project.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public Page<Message> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    public Page<Message> getMessagesByUser(Long userId, Pageable pageable) {
        return messageRepository.findBySenderId(userId, pageable);
    }

    @Transactional
    public Message updateMessage(Long id, Message messageDetails) {
        return messageRepository.findById(id)
                .map(message -> {
                    if (messageDetails.getContent() != null) {
                        message.setContent(messageDetails.getContent());
                    }
                    if (messageDetails.getIsRead() != null) {
                        message.setIsRead(messageDetails.getIsRead());
                    }
                    if (messageDetails.getMessageType() != null) {
                        message.setMessageType(messageDetails.getMessageType());
                    }
                    return messageRepository.save(message);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
    }

    @Transactional
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    @Transactional
    public void markMessagesAsRead(List<Long> messageIds) {
        messageRepository.markMessagesAsRead(messageIds);
    }

    @Transactional
    public int cleanupOldMessages(int daysToKeep) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
        return messageRepository.deleteMessagesOlderThan(cutoffDate);
    }

    public Page<Message> searchMessages(Long senderId, String content,
                                        LocalDateTime startDate, LocalDateTime endDate,
                                        Pageable pageable) {
        return messageRepository.searchMessages(senderId, content, startDate, endDate, pageable);
    }

    public List<Message> getRecentActiveMessages() {
        return messageRepository.findRecentActiveMessages();
    }
}