-- V3__Create_Foods_Table.sql

-- Create foods table if it doesn't exist
CREATE TABLE IF NOT EXISTS foods
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    description TEXT,
    category VARCHAR
(
    50
) NOT NULL,
    available BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Create index if it doesn't exist
DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1 FROM pg_indexes
        WHERE indexname = 'idx_foods_name'
    ) THEN
CREATE INDEX idx_foods_name ON foods (name);
END IF;
END$$;

-- Create or replace the update timestamp trigger function
CREATE
OR REPLACE FUNCTION update_foods_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
language 'plpgsql';

-- Drop the trigger if it exists and create it again
DROP TRIGGER IF EXISTS update_foods_updated_at ON foods;
CREATE TRIGGER update_foods_updated_at
    BEFORE UPDATE
    ON foods
    FOR EACH ROW
    EXECUTE FUNCTION update_foods_updated_at();