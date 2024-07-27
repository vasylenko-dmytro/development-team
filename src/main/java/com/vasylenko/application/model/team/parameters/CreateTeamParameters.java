package com.vasylenko.application.model.team.parameters;

import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.user.UserId;

import java.util.Set;

public class CreateTeamParameters {
    private final String name;
    private final UserId leadId;
    private final Set<TeamMemberParameters> members;

    public CreateTeamParameters(String name, UserId leadId, Set<TeamMemberParameters> members) {
        this.name = name;
        this.leadId = leadId;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public UserId getLeadId() {
        return leadId;
    }

    public Set<TeamMemberParameters> getMembers() {
        return members;
    }
}
