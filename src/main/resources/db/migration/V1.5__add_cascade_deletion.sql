-- Drop the existing foreign key constraint
ALTER TABLE team_member DROP CONSTRAINT fk_team_member_to_user;
ALTER TABLE team DROP CONSTRAINT fk_team_to_user;

-- Add a new foreign key constraint with ON DELETE CASCADE
ALTER TABLE team_member
    ADD CONSTRAINT fk_team_member_to_user
        FOREIGN KEY (member_id) REFERENCES tt_user(id) ON DELETE CASCADE;
ALTER TABLE team
    ADD CONSTRAINT fk_team_to_user
        FOREIGN KEY (lead_id) REFERENCES tt_user(id) ON DELETE CASCADE;