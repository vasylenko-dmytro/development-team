package com.vasylenko.application.repository;

import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.TeamSummary;
import com.vasylenko.application.model.team.member.TeamMemberId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for Team entities.
 */
@Transactional(readOnly = true)
public interface TeamRepository extends CrudRepository<Team, TeamId> {

    /**
     * Generates the next unique Team ID.
     *
     * @return the next Team ID
     */
    TeamId nextId();

    /**
     * Generates the next unique Team Member ID.
     *
     * @return the next Team Member ID
     */
    TeamMemberId nextMemberId();

    /**
     * Finds all team summaries with pagination.
     *
     * @param pageable the pagination information
     * @return a page of team summaries
     */
    @Query("SELECT new com.vasylenko.application.model.team.TeamSummary(t, t.name, t.lead) FROM Team t")
    Page<TeamSummary> findAllSummary(Pageable pageable);

    /**
     * Finds a team with its members by team ID.
     *
     * @param id the team ID
     * @return an optional containing the team with its members if found
     */
    @Query("FROM Team t JOIN FETCH t.members WHERE t.id = :id")
    Optional<Team> findTeamWithMembers(@Param("id") TeamId id);
}
