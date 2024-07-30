package com.vasylenko.application.service;

import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.TeamSummary;
import com.vasylenko.application.model.team.parameters.CreateTeamParameters;
import com.vasylenko.application.model.team.parameters.EditTeamParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for managing teams.
 */
public interface TeamService {

    /**
     * Retrieves a paginated list of team summaries.
     *
     * @param pageable the pagination information
     * @return a page of team summaries
     */
    Page<TeamSummary> getTeams(Pageable pageable);

    /**
     * Creates a new team with the given parameters.
     *
     * @param parameters the parameters for creating the team
     */
    void createTeam(CreateTeamParameters parameters);

    /**
     * Edits an existing team with the given parameters.
     *
     * @param teamId the ID of the team to edit
     * @param parameters the parameters for editing the team
     */
    void editTeam(TeamId teamId, EditTeamParameters parameters);

    /**
     * Retrieves a team by its ID.
     *
     * @param teamId the ID of the team to retrieve
     * @return an optional containing the team if found
     */
    Optional<Team> getTeam(TeamId teamId);

    /**
     * Retrieves a team with its members by its ID.
     *
     * @param teamId the ID of the team to retrieve
     * @return an optional containing the team with its members if found
     */
    Optional<Team> getTeamWithMembers(TeamId teamId);

    /**
     * Deletes a team by its ID.
     *
     * @param teamId the ID of the team to delete
     */
    void deleteTeam(TeamId teamId);

    /**
     * Deletes all teams.
     */
    void deleteAllTeams();
}
