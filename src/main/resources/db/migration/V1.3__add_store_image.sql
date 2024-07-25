-- Add the 'avatar' column to 'tt_user' table
ALTER TABLE tt_user
    ADD COLUMN avatar BYTEA;