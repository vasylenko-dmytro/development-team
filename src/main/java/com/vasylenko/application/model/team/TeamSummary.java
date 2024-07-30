package com.vasylenko.application.model.team;

import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.model.user.UserName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Class representing a summary of a team.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class TeamSummary {
    private final TeamId id;
    private final String name;
    private final UserId leadId;
    private final UserName leadName;

    /**
     * Constructs a new TeamSummary with the given details.
     *
     * @param team the team entity
     * @param name the name of the team
     * @param lead the lead user of the team
     */
    public TeamSummary(Team team, String name, User lead) {
        this(team.getId(), name, lead.getId(), lead.getUserName());
    }
}