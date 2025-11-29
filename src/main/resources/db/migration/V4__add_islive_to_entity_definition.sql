ALTER TABLE entity_definition
    ADD is_live BIT(1) NULL;

UPDATE entity_definition
SET is_live = 1
WHERE is_live IS NULL;
ALTER TABLE entity_definition
    MODIFY is_live BIT(1) NOT NULL;