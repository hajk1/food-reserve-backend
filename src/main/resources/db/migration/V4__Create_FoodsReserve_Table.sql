CREATE TABLE food_reservations
(
    id               VARCHAR(36) PRIMARY KEY,
    food_id          VARCHAR(36) NOT NULL,
    person_id        VARCHAR(36) NOT NULL,
    reservation_date DATE        NOT NULL,
    status           VARCHAR(20) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_food_reservations_person_id ON food_reservations (person_id);
CREATE INDEX idx_food_reservations_date ON food_reservations (reservation_date);

-- Trigger for updated_at
CREATE TRIGGER update_food_reservations_updated_at
    BEFORE UPDATE
    ON food_reservations
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();