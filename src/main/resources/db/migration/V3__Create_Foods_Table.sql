-- V3__Create_Foods_Table.sql

CREATE TABLE foods
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    category    VARCHAR(50)  NOT NULL,
    image_url   VARCHAR(255),
    available   BOOLEAN      NOT NULL DEFAULT true,
    created_at  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_foods_category ON foods (category);
CREATE INDEX idx_foods_available ON foods (available);

-- Create trigger for updating timestamp
CREATE TRIGGER update_foods_updated_at
    BEFORE UPDATE
    ON foods
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Insert sample data
INSERT INTO foods (name, description, category, available)
VALUES ('Spaghetti Carbonara', 'Classic Italian pasta dish', 'MAIN_COURSE', true),
       ('Caesar Salad', 'Fresh romaine lettuce with Caesar dressing', 'SALAD', true);