-- 清空表（可选，避免重复插入）
TRUNCATE TABLE users;
TRUNCATE TABLE messages;

-- 插入测试用户（密码需加密）
INSERT INTO users (username, password, status, created_at) VALUES
                                                               ('alice', '$2a$10$xJwL5v5Jz5U5Z5U5Z5U5Zu', 'ACTIVE', '2023-08-01 10:00:00'), -- 密码: 123456
                                                               ('bob', '$2a$10$xJwL5v5Jz5U5Z5U5Z5U5Zu', 'ACTIVE', '2023-08-01 11:00:00'),
                                                               ('charlie', '$2a$10$xJwL5v5Jz5U5Z5U5Z5U5Zu', 'INACTIVE', '2023-08-02 09:00:00');

-- 插入测试消息
INSERT INTO messages (sender_id, content, is_read, created_at) VALUES
                                                                   (1, 'Hello everyone!', false, '2023-08-10 14:00:00'),
                                                                   (2, 'Hi Alice!', false, '2023-08-10 14:05:00'),
                                                                   (1, 'How are you doing?', true, '2023-08-10 14:10:00'),
                                                                   (3, 'Is anyone there?', false, '2023-08-10 14:15:00');