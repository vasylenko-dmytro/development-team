CREATE TABLE team
(
    id       UUID    NOT NULL,
    version  BIGINT  NOT NULL,
    name     VARCHAR NOT NULL,
    lead_id UUID    NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE team
    ADD CONSTRAINT FK_team_to_user FOREIGN KEY (lead_id) REFERENCES tt_user;

CREATE TABLE team_member
(
    id        UUID    NOT NULL,
    team_id   UUID    NOT NULL,
    member_id UUID    NOT NULL,
    position  VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE team_member
    ADD CONSTRAINT FK_team_member_to_team FOREIGN KEY (team_id) REFERENCES team;
ALTER TABLE team_member
    ADD CONSTRAINT FK_team_member_to_user FOREIGN KEY (member_id) REFERENCES tt_user;