package com.vasylenko.application.service;

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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository repository;
    private final UserService userService;

    /**
     * Retrieves a paginated list of team summaries.
     *
     * @param pageable the pagination information
     * @return a page of team summaries
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamSummary> getTeams(Pageable pageable) {
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
        LOGGER.info("Creating team {} with lead {} ({})", name, lead.getUserName().getFullName(), lead.getId());
        Team team = new Team(repository.nextId(), name, lead);
        Set<TeamMemberParameters> members = parameters.getMembers();
        for (TeamMemberParameters member : members) {
            team.addMember(new TeamMember(repository.nextMemberId(), getUser(member.memberId()), member.position()));
        }
        repository.save(team);
    }

    /**
     * Edits an existing team with the given parameters.
     *
     * @param teamId the ID of the team to edit
     * @param parameters the parameters for editing the team
     */
    @Override
    public void editTeam(TeamId teamId, EditTeamParameters parameters) {
        Team team = getTeam(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        if (team.getVersion() != parameters.getVersion()) {
            throw new ObjectOptimisticLockingFailureException(User.class, team.getId().asString());
        }
        team.setName(parameters.getName());
        team.setLead(getUser(parameters.getLeadId()));
        team.setMembers(parameters.getMembers().stream()
                .map(teamMemberParameters -> new TeamMember(repository.nextMemberId(),
                        getUser(teamMemberParameters.memberId()), teamMemberParameters.position()))
                .collect(Collectors.toSet()));

    }

    /**
     * Retrieves a team by its ID.
     *
     * @param teamId the ID of the team to retrieve
     * @return an optional containing the team if found
     */
    @Override
    public Optional<Team> getTeam(TeamId teamId) {
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
        return repository.findTeamWithMembers(teamId);
    }

    /**
     * Deletes a team by its ID.
     *
     * @param teamId the ID of the team to delete
     */
    @Override
    public void deleteTeam(TeamId teamId) {
        repository.deleteById(teamId);
    }

    /**
     * Deletes all teams.
     */
    @Override
    public void deleteAllTeams() {
        repository.deleteAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user
     * @throws UserNotFoundException if the user is not found
     */
    private User getUser(UserId userId) {
        return userService.getUser(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
