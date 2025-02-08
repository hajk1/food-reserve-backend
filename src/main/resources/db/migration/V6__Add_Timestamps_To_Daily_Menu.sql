DO
$$
BEGIN
    -- Add timestamp columns if they don't exist
    IF
NOT EXISTS (SELECT 1 FROM information_schema.columns
                  WHERE table_name = 'daily_menu' AND column_name = 'created_at') THEN
ALTER TABLE daily_menu
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
END IF;

    IF
NOT EXISTS (SELECT 1 FROM information_schema.columns
                  WHERE table_name = 'daily_menu' AND column_name = 'updated_at') THEN
ALTER TABLE daily_menu
    ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
END IF;

    -- Check if trigger exists
    IF
NOT EXISTS (SELECT 1 FROM pg_trigger
                  WHERE tgname = 'update_daily_menu_updated_at'
                  AND tgrelid = 'daily_menu'::regclass) THEN
-- Create trigger only if it doesn't exist
CREATE TRIGGER update_daily_menu_updated_at
    BEFORE UPDATE
    ON daily_menu
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
END IF;

EXCEPTION
    WHEN others THEN
        RAISE NOTICE 'Error occurred: %', SQLERRM;
        RAISE;
END $$;