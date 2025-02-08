-- V4__Create_Daily_Menu_Table.sql

-- Create daily_menu table if it doesn't exist
CREATE TABLE IF NOT EXISTS daily_menu
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    food_id
    BIGINT
    NOT
    NULL,
    date
    DATE
    NOT
    NULL,
    max_servings
    INTEGER
    NOT
    NULL,
    remaining_servings
    INTEGER
    NOT
    NULL,
    created_at
    TIMESTAMP
    DEFAULT
    CURRENT_TIMESTAMP,
    updated_at
    TIMESTAMP
    DEFAULT
    CURRENT_TIMESTAMP,
    CONSTRAINT
    fk_daily_menu_food
    FOREIGN
    KEY
(
    food_id
) REFERENCES foods
(
    id
) ON DELETE CASCADE
    );

-- Create indexes if they don't exist
DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1 FROM pg_indexes
        WHERE indexname = 'idx_daily_menu_date'
    ) THEN
CREATE INDEX idx_daily_menu_date ON daily_menu (date);
END IF;

    IF
NOT EXISTS (
        SELECT 1 FROM pg_indexes
        WHERE indexname = 'idx_daily_menu_food_date'
    ) THEN
CREATE INDEX idx_daily_menu_food_date ON daily_menu (food_id, date);
END IF;
END$$;

-- Create or replace the update timestamp trigger function
CREATE
OR REPLACE FUNCTION update_daily_menu_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
language 'plpgsql';

-- Drop the trigger if it exists and create it again
DROP TRIGGER IF EXISTS update_daily_menu_updated_at ON daily_menu;
CREATE TRIGGER update_daily_menu_updated_at
    BEFORE UPDATE
    ON daily_menu
    FOR EACH ROW
    EXECUTE FUNCTION update_daily_menu_updated_at();