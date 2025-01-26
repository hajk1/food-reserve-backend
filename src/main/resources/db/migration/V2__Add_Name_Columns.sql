-- V2__Add_Name_Columns.sql

-- Add firstName and lastName columns to app_users table
ALTER TABLE app_users
    ADD COLUMN first_name VARCHAR(50),
    ADD COLUMN last_name VARCHAR(50);

-- Update existing admin user with sample name
UPDATE app_users
SET first_name = 'Admin',
    last_name  = 'User'
WHERE username = 'admin';