CREATE TABLE daily_menu
(
    id                 BIGSERIAL PRIMARY KEY,
    food_id            BIGINT  NOT NULL,
    date               DATE    NOT NULL,
    max_servings       INTEGER NOT NULL,
    remaining_servings INTEGER NOT NULL,
    CONSTRAINT fk_daily_menu_food FOREIGN KEY (food_id) REFERENCES foods (id),
    CONSTRAINT uk_food_date UNIQUE (food_id, date)
);

CREATE INDEX idx_daily_menu_date ON daily_menu (date);