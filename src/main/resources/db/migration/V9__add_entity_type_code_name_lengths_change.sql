ALTER TABLE entity_definition
    ADD entity_type VARCHAR(255) NULL;

UPDATE entity_definition
SET entity_type = ''
WHERE entity_type IS NULL;
ALTER TABLE entity_definition
    MODIFY entity_type VARCHAR(255) NOT NULL;

ALTER TABLE entity_definition
    MODIFY entity_code VARCHAR(65);

ALTER TABLE entity_definition
    MODIFY entity_name VARCHAR(100);