-- V1__Create_User_Tables.sql

-- Users table
CREATE TABLE app_users (
                           id BIGSERIAL PRIMARY KEY,
                           username VARCHAR(50) NOT NULL UNIQUE,
                           password VARCHAR(100) NOT NULL,
                           enabled BOOLEAN NOT NULL DEFAULT TRUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User roles table
CREATE TABLE app_user_roles (
                                user_id BIGINT NOT NULL,
                                role VARCHAR(50) NOT NULL,
                                CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id) REFERENCES app_users(id) ON DELETE CASCADE,
                                PRIMARY KEY (user_id, role)
);

-- Create indexes
CREATE INDEX idx_app_users_username ON app_users(username);
CREATE INDEX idx_app_user_roles_user_id ON app_user_roles(user_id);

-- Create function for updating timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ language 'plpgsql';

-- Create trigger for updating timestamp
CREATE TRIGGER update_app_users_updated_at
    BEFORE UPDATE ON app_users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Insert default admin user (password: admin123)
INSERT INTO app_users (username, password, enabled)
VALUES ('admin', '$2a$10$AQIf00u4Un33oubOLCqJuObmxMdjRBB7VLN.B/ZYcOtlQ28j4MZwy', true);

INSERT INTO app_user_roles (user_id, role)
VALUES (1, 'ADMIN');

INSERT INTO app_user_roles (user_id, role)
VALUES (1, 'USER');