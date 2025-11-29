
INSERT INTO users (username, email, password, role, enabled, created_at, updated_at, created_by, updated_by)
VALUES (
    'admin',
    'admin@finalproject.com',
    '$2a$10$y/ZzMRoPzRuBFC5yI.f2uugQbw4jrGm8AkVBeF6VsfQHh8hHax02y',
    'ADMIN',
    TRUE,
    NOW(),
    NOW(),
    'SYSTEM',
    'SYSTEM'
);
