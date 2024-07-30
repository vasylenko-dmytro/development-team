package com.vasylenko.application.model.team;

import com.vasylenko.application.model.entity.AbstractEntity;
import com.vasylenko.application.model.team.member.TeamMember;
import com.vasylenko.application.model.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a team.
 */
@Entity
@Getter
@Setter
public class Team extends AbstractEntity<TeamId> {

    @Version
    private long version;

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User lead;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamMember> members;

    /**
     * Protected default constructor for JPA
     */
    protected Team() {
    }

    /**
     * Constructs a new Team with the given details.
     *
     * @param id the team ID
     * @param name the name of the team
     * @param lead the lead user of the team
     */
    public Team(TeamId id, String name, User lead) {
        super(id);
        this.name = name;
        this.lead = lead;
        this.members = new HashSet<>();
    }

    /**
     * Adds a member to the team.
     *
     * @param member the team member to add
     */
    public void addMember(TeamMember member) {
        members.add(member);
        member.setTeam(this);
    }

    /**
     * Sets the members of the team.
     *
     * @param members the set of team members
     */
    public void setMembers(Set<TeamMember> members) {
        this.members.clear();
        for (TeamMember member : members) {
            addMember(member);
        }
    }
}
