package com.vasylenko.application.formdata;

import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.parameters.EditTeamParameters;

public class EditTeamFormData extends CreateTeamFormData {
    private String id;
    private long version;

    public static EditTeamFormData fromTeam(Team team) {
        EditTeamFormData result = new EditTeamFormData();
        result.setId(team.getId().asString());
        result.setVersion(team.getVersion());
        result.setName(team.getName());
        result.setLeadId(team.getLead().getId());
        result.setMembers(team.getMembers().stream()
                .map(TeamMemberFormData::fromTeamMember)
                .toArray(TeamMemberFormData[]::new));
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public EditTeamParameters toParameters() {
        return new EditTeamParameters(version,
                getName(),
                getLeadId(),
                getTeamMemberParameters());
    }
}
