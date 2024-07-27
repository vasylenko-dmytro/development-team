package com.vasylenko.application.model.team.member;

import com.vasylenko.application.model.user.UserId;

public class TeamMemberParameters {
    private final UserId memberId;
    private final TeamMemberPosition position;

    public TeamMemberParameters(UserId memberId, TeamMemberPosition position) {
        this.memberId = memberId;
        this.position = position;
    }

    public UserId getMemberId() {
        return memberId;
    }

    public TeamMemberPosition getPosition() {
        return position;
    }
}
