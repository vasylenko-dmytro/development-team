package com.vasylenko.application.formdata;

import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.parameters.EditTeamParameters;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Form data for editing a team.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EditTeamFormData extends CreateTeamFormData {

    private String id;

    private long version;

    /**
     * Creates an instance of EditTeamFormData from a Team object.
     *
     * @param team the team object
     * @return the EditTeamFormData instance
     */
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

    /**
     * Converts the form data to edit team parameters.
     *
     * @return the edit team parameters
     */
    @Override
    public EditTeamParameters toParameters() {
        return new EditTeamParameters(version,
                getName(),
                getLeadId(),
                getTeamMemberParameters());
    }
}
