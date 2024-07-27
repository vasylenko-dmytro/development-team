package com.vasylenko.application.model.team;

import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.model.user.UserName;

public class TeamSummary {
    private final TeamId id;
    private final String name;
    private final UserId leadId;
    private final UserName leadName;

    public TeamSummary(Team team, String name, User lead) {
        this.id = team.getId();
        this.name = name;
        this.leadId = lead.getId();
        this.leadName = lead.getUserName();
    }

    public TeamId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserId getLeadId() {
        return leadId;
    }

    public UserName getLeadName() {
        return leadName;
    }
}