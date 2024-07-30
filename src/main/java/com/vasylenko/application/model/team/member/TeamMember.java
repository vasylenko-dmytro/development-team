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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a team member.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
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

    /**
     * Constructs a new TeamMember with the given details.
     *
     * @param id the ID of the team member
     * @param member the user who is a member of the team
     * @param position the position of the team member
     */
    public TeamMember(TeamMemberId id, User member, TeamMemberPosition position) {
        super(id);
        this.member = member;
        this.position = position;
    }
}
