package com.vasylenko.application.service.impl;

import com.vasylenko.application.exception.TeamNotFoundException;
import com.vasylenko.application.exception.UserNotFoundException;
import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.member.TeamMember;
import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.team.TeamSummary;
import com.vasylenko.application.model.team.parameters.CreateTeamParameters;
import com.vasylenko.application.model.team.parameters.EditTeamParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.repository.TeamRepository;
import com.vasylenko.application.service.TeamService;
import com.vasylenko.application.service.UserService;
import com.vasylenko.application.util.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing teams.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;
    private final UserService userService;
    private final CustomLogger customLogger;

    @Autowired
    public TeamServiceImpl(TeamRepository repository, UserService userService, CustomLogger customLogger) {
        this.repository = repository;
        this.userService = userService;
        this.customLogger = customLogger;
    }

    /**
     * Retrieves a paginated list of team summaries.
     *
     * @param pageable the pagination information
     * @return a page of team summaries
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamSummary> getTeams(Pageable pageable) {
        customLogger.info(String.format("Retrieving paginated list of team summaries with pagination: %s", pageable));

        return repository.findAllSummary(pageable);
    }


    /**
     * Creates a new team with the given parameters.
     *
     * @param parameters the parameters for creating the team
     */
    @Override
    public void createTeam(CreateTeamParameters parameters) {
        String name = parameters.getName();
        User lead = getUser(parameters.getLeadId());
        customLogger.info(String.format("Creating team %s with lead %s (%s)", name, lead.getUserName().getFullName(), lead.getId()));

        Team team = new Team(repository.nextId(), name, lead);
        Set<TeamMemberParameters> members = parameters.getMembers();
        for (TeamMemberParameters member : members) {
            team.addMember(new TeamMember(repository.nextMemberId(), getUser(member.memberId()), member.position()));
        }
        repository.save(team);
        customLogger.info(String.format("Team %s created successfully", name));
    }

    /**
     * Edits an existing team with the given parameters.
     *
     * @param teamId the ID of the team to edit
     * @param parameters the parameters for editing the team
     */
    @Override
    public void editTeam(TeamId teamId, EditTeamParameters parameters) {
        customLogger.info(String.format("Editing team with ID: %s", teamId));

        Team team = getTeam(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        if (team.getVersion() != parameters.getVersion()) {
            customLogger.warn(String.format("Version conflict for team with ID: %s", teamId));
            throw new ObjectOptimisticLockingFailureException(User.class, team.getId().asString());
        }
        team.setName(parameters.getName());
        team.setLead(getUser(parameters.getLeadId()));
        team.setMembers(parameters.getMembers().stream()
                .map(teamMemberParameters -> new TeamMember(repository.nextMemberId(),
                        getUser(teamMemberParameters.memberId()), teamMemberParameters.position()))
                .collect(Collectors.toSet()));
        customLogger.info(String.format("Team with ID: %s edited successfully", teamId));
    }

    /**
     * Retrieves a team by its ID.
     *
     * @param teamId the ID of the team to retrieve
     * @return an optional containing the team if found
     */
    @Override
    public Optional<Team> getTeam(TeamId teamId) {
        customLogger.info(String.format("Retrieving team with ID: %s", teamId));

        return repository.findById(teamId);
    }

    /**
     * Retrieves a team with its members by its ID.
     *
     * @param teamId the ID of the team to retrieve
     * @return an optional containing the team with its members if found
     */
    @Override
    public Optional<Team> getTeamWithMembers(TeamId teamId) {
        customLogger.info(String.format("Retrieving team with members by ID: %s", teamId));

        return repository.findTeamWithMembers(teamId);
    }

    /**
     * Deletes a team by its ID.
     *
     * @param teamId the ID of the team to delete
     */
    @Override
    public void deleteTeam(TeamId teamId) {
        customLogger.info(String.format("Deleting team with ID: %s", teamId));

        repository.deleteById(teamId);

        customLogger.info(String.format("Team with ID: %s deleted successfully", teamId));
    }

    /**
     * Deletes all teams.
     */
    @Override
    public void deleteAllTeams() {
        customLogger.info("Deleting all teams");

        repository.deleteAll();

        customLogger.info("All teams deleted successfully");
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user
     * @throws UserNotFoundException if the user is not found
     */
    private User getUser(UserId userId) {
        customLogger.info(String.format("Retrieving user with ID: %s", userId));

        return userService.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
