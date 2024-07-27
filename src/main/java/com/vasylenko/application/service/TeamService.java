package com.vasylenko.application.service;

import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.TeamSummary;
import com.vasylenko.application.model.team.parameters.CreateTeamParameters;
import com.vasylenko.application.model.team.parameters.EditTeamParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TeamService {
    Page<TeamSummary> getTeams(Pageable pageable);
    Team createTeam(CreateTeamParameters parameters);
    Team editTeam(TeamId teamId, EditTeamParameters parameters);
    Optional<Team> getTeam(TeamId teamId);
    Optional<Team> getTeamWithMembers(TeamId teamId);
    void deleteTeam(TeamId teamId);
    void deleteAllTeams();
}
