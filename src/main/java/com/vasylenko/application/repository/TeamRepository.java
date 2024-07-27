package com.vasylenko.application.repository;

import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.TeamSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface TeamRepository extends CrudRepository<Team, TeamId>, TeamRepositoryCustom {

    @Query("SELECT new com.vasylenko.application.model.team.TeamSummary(t, t.name, t.lead) FROM Team t")
    Page<TeamSummary> findAllSummary(Pageable pageable);
    @Query("FROM Team t JOIN FETCH t.members WHERE t.id = :id")
    Optional<Team> findTeamWithMembers(@Param("id") TeamId id);
}
