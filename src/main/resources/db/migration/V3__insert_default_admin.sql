INSERT INTO users (username, email, password, role, enabled, created_at, updated_at, created_by, updated_by)
VALUES (
    'admin',
    'admin@finalproject.com',
    '$2a$10$xLnRvGvFqz6YKjKqBf8GE.1QOW8YDxJI7P7aZ8N3KvYj9sMQxXqMm',
    'ADMIN',
    TRUE,
    NOW(),
    NOW(),
    'SYSTEM',
    'SYSTEM'
);