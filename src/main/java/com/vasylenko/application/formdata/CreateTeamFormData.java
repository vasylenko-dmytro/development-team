package com.vasylenko.application.formdata;

import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.team.parameters.CreateTeamParameters;
import com.vasylenko.application.model.user.UserId;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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

    public CreateTeamFormData() {
        this.members = new TeamMemberFormData[]{new TeamMemberFormData()};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserId getLeadId() {
        return leadId;
    }

    public void setLeadId(UserId leadId) {
        this.leadId = leadId;
    }

    public TeamMemberFormData[] getMembers() {
        return members;
    }

    public void setMembers(TeamMemberFormData[] members) {
        this.members = members;
    }

    public CreateTeamParameters toParameters() {
        return new CreateTeamParameters(name, leadId, getTeamMemberParameters());
    }

    @Nonnull
    protected Set<TeamMemberParameters> getTeamMemberParameters() {
        return Arrays.stream(members)
                .map(teamMemberFormData -> new TeamMemberParameters(teamMemberFormData.getMemberId(),
                        teamMemberFormData.getPosition()))
                .collect(Collectors.toSet());
    }

    public void removeEmptyTeamMemberForms() {
        setMembers(Arrays.stream(members)
                .filter(this::isNotEmptyTeamMemberForm)
                .toArray(TeamMemberFormData[]::new));
    }

    private boolean isNotEmptyTeamMemberForm(TeamMemberFormData formData) {
        return formData != null
                && formData.getMemberId() != null
                && formData.getPosition() != null;
    }
}
