package com.vasylenko.application.model.team.parameters;

import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.user.UserId;

import java.util.Set;

public class EditTeamParameters extends CreateTeamParameters {
    private final long version;

    public EditTeamParameters(long version, String name, UserId leadId, Set<TeamMemberParameters> members) {
        super(name, leadId, members);
        this.version = version;
    }

    public long getVersion() {
        return version;
    }
}
