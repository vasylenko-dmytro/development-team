package com.vasylenko.application.formdata;

import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.team.parameters.CreateTeamParameters;
import com.vasylenko.application.model.user.UserId;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Form data for creating a team.
 */
@Data
public class CreateTeamFormData {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private UserId leadId;

    @NotNull
    @Size(min = 1)
    @Valid
    private TeamMemberFormData[] members;

    /**
     * Default constructor initializing with one empty team member.
     */
    public CreateTeamFormData() {
        this.members = new TeamMemberFormData[]{new TeamMemberFormData()};
    }


    /**
     * Converts the form data to create team parameters.
     *
     * @return the create team parameters
     */
    public CreateTeamParameters toParameters() {
        return new CreateTeamParameters(name, leadId, getTeamMemberParameters());
    }

    /**
     * Gets the team member parameters.
     *
     * @return the set of team member parameters
     */
    @Nonnull
    protected Set<TeamMemberParameters> getTeamMemberParameters() {
        return Arrays.stream(members)
                .map(teamMemberFormData -> new TeamMemberParameters(teamMemberFormData.getMemberId(),
                        teamMemberFormData.getPosition()))
                .collect(Collectors.toSet());
    }

    /**
     * Removes empty team member forms.
     */
    public void removeEmptyTeamMemberForms() {
        setMembers(Arrays.stream(members)
                .filter(this::isNotEmptyTeamMemberForm)
                .toArray(TeamMemberFormData[]::new));
    }

    /**
     * Checks if the team member form is not empty.
     *
     * @param formData the team member form data
     * @return true if the form data is not empty, false otherwise
     */
    private boolean isNotEmptyTeamMemberForm(TeamMemberFormData formData) {
        return formData != null
                && formData.getMemberId() != null
                && formData.getPosition() != null;
    }
}
