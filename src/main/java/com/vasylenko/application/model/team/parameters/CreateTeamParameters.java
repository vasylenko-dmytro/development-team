package com.vasylenko.application.model.team.parameters;

import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * Class representing the parameters for creating a team.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class CreateTeamParameters {
    private final String name;
    private final UserId leadId;
    private final Set<TeamMemberParameters> members;
}
