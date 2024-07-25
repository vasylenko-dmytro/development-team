-- Add the 'password' column to 'tt_user' table
ALTER TABLE tt_user
    ADD COLUMN password VARCHAR NOT NULL;

-- Add a unique constraint on the 'email' column
ALTER TABLE tt_user
    ADD CONSTRAINT UK_user_email UNIQUE (email);

-- Create the 'user_roles' table
CREATE TABLE user_roles
(
    user_id UUID    NOT NULL,
    role    VARCHAR NOT NULL
);

-- Add the foreign key constraint from 'user_roles' to 'tt_user'
ALTER TABLE user_roles
    ADD CONSTRAINT FK_user_roles_to_user FOREIGN KEY (user_id) REFERENCES tt_user (id);