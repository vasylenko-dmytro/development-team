package com.vasylenko.application.model.team;

import com.vasylenko.application.model.entity.AbstractVersionedEntity;
import com.vasylenko.application.model.team.member.TeamMember;
import com.vasylenko.application.model.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Team extends AbstractVersionedEntity<TeamId> {

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User lead;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamMember> members;

    /**
     * Default constructor for JPA
     */
    protected Team() {
    }

    public Team(TeamId id,
                String name,
                User lead) {
        super(id);
        this.name = name;
        this.lead = lead;
        this.members = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getLead() {
        return lead;
    }

    public void setLead(User lead) {
        this.lead = lead;
    }

    public Set<TeamMember> getMembers() {
        return members;
    }

    public void addMember(TeamMember member) {
        members.add(member);
        member.setTeam(this);
    }

    public void setMembers(Set<TeamMember> members) {
        this.members.clear();
        for (TeamMember member : members) {
            addMember(member);
        }
    }
}
