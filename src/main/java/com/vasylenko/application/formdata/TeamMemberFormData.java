package com.vasylenko.application.formdata;

import com.vasylenko.application.model.team.member.TeamMember;
import com.vasylenko.application.model.team.member.TeamMemberPosition;
import com.vasylenko.application.model.user.UserId;
import jakarta.validation.constraints.NotNull;

public class TeamMemberFormData {
    @NotNull
    private UserId memberId;
    @NotNull
    private TeamMemberPosition position;

    public UserId getMemberId() {
        return memberId;
    }

    public void setMemberId(UserId memberId) {
        this.memberId = memberId;
    }

    public TeamMemberPosition getPosition() {
        return position;
    }

    public void setPosition(TeamMemberPosition position) {
        this.position = position;
    }

    public static TeamMemberFormData fromTeamMember(TeamMember member) {
        TeamMemberFormData result = new TeamMemberFormData();
        result.setMemberId(member.getMember().getId());
        result.setPosition(member.getPosition());
        return result;
    }
}
