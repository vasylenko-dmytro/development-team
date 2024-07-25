-- Add the 'password' column to 'tt_user' table
ALTER TABLE tt_user
    ADD COLUMN password VARCHAR NOT NULL;

-- Add a unique constraint on the 'email' column
ALTER TABLE tt_user
    ADD CONSTRAINT UK_user_email UNIQUE (email);