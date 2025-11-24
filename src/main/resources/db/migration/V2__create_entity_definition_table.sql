CREATE TABLE entity_definition (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_code VARCHAR(255) NOT NULL UNIQUE,
    entity_name VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    INDEX idx_entity_code (entity_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;