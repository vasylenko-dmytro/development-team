package com.vasylenko.application.model.team.member;

import com.vasylenko.application.model.entity.AbstractEntity;
import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class TeamMember extends AbstractEntity<TeamMemberId> {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Team team;

    @OneToOne
    @NotNull
    private User member;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TeamMemberPosition position;

    protected TeamMember() {
    }

    public TeamMember(TeamMemberId id,
                      User member,
                      TeamMemberPosition position) {
        super(id);
        this.member = member;
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getMember() {
        return member;
    }

    public TeamMemberPosition getPosition() {
        return position;
    }
}
