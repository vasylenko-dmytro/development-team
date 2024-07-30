package com.vasylenko.application.model.team.parameters;

import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class EditTeamParameters extends CreateTeamParameters {
    private final long version;

    /**
     * Constructs a new EditTeamParameters with the given details.
     *
     * @param version the version of the team
     * @param name the name of the team
     * @param leadId the lead user id of the team
     * @param members the members of the team
     */
    public EditTeamParameters(long version, String name, UserId leadId, Set<TeamMemberParameters> members) {
        super(name, leadId, members);
        this.version = version;
    }
}
