-- Create the 'user_roles' table
CREATE TABLE user_roles
(
    user_id UUID    NOT NULL,
    role    VARCHAR NOT NULL
);

-- Add the foreign key constraint from 'user_roles' to 'tt_user'
ALTER TABLE user_roles
    ADD CONSTRAINT FK_user_roles_to_user FOREIGN KEY (user_id) REFERENCES tt_user (id);