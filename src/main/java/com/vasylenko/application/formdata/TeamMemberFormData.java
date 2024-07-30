package com.vasylenko.application.formdata;

import com.vasylenko.application.model.team.member.TeamMember;
import com.vasylenko.application.model.team.member.TeamMemberPosition;
import com.vasylenko.application.model.user.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Form data for a team member.
 */
@Data
public class TeamMemberFormData {

    @NotNull
    private UserId memberId;

    @NotNull
    private TeamMemberPosition position;

    /**
     * Creates an instance of TeamMemberFormData from a TeamMember object.
     *
     * @param member the team member object
     * @return the TeamMemberFormData instance
     */
    public static TeamMemberFormData fromTeamMember(TeamMember member) {
        TeamMemberFormData result = new TeamMemberFormData();
        result.setMemberId(member.getMember().getId());
        result.setPosition(member.getPosition());
        return result;
    }
}
