package com.vasylenko.application.repository;

import com.vasylenko.application.helper.Users;
import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.TeamSummary;
import com.vasylenko.application.model.team.member.TeamMember;
import com.vasylenko.application.model.team.member.TeamMemberPosition;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserName;
import com.vasylenko.application.util.InMemoryUniqueIdGenerator;
import com.vasylenko.application.util.UniqueIdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
@ActiveProfiles("data-jpa-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamRepositoryTest {
    private final TeamRepository repository;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    TeamRepositoryTest(TeamRepository repository,
                       UserRepository userRepository,
                       JdbcTemplate jdbcTemplate) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void validatePreconditions() {
        assertThat(repository.count()).isZero();
    }

    @Test
    void testSaveTeam() {
        User user = userRepository.save(Users.createUser(new UserName("Harlan", "Rees")));

        TeamId id = repository.nextId();
        repository.save(new Team(id, "Initiates", user));

        entityManager.flush();

        assertThat(jdbcTemplate.queryForObject("SELECT id FROM team", UUID.class)).isEqualTo(id.getId());
        assertThat(jdbcTemplate.queryForObject("SELECT name FROM team", String.class)).isEqualTo("Initiates");
        assertThat(jdbcTemplate.queryForObject("SELECT lead_id FROM team", UUID.class)).isEqualTo(user.getId().getId());
    }

    @Test
    void testFindAllSummary() {
        User user = userRepository.save(Users.createUser(new UserName("Harlan", "Rees")));

        TeamId id = repository.nextId();
        repository.save(new Team(id, "Initiates", user));

        Page<TeamSummary> teams = repository.findAllSummary(PageRequest.of(0, 10));
        assertThat(teams).hasSize(1)
                .extracting(TeamSummary::getId,
                        TeamSummary::getName,
                        TeamSummary::getLeadId,
                        TeamSummary::getLeadName)
                .contains(tuple(id,
                        "Initiates",
                        user.getId(),
                        user.getUserName()));
    }

    @Test
    void testSaveTeamWithMembers() {
        User lead = userRepository.save(Users.createUser(new UserName("Lead", "1")));
        User member1 = userRepository.save(Users.createUser(new UserName("Member", "1")));
        User member2 = userRepository.save(Users.createUser(new UserName("Member", "2")));
        User member3 = userRepository.save(Users.createUser(new UserName("Member", "3")));

        TeamId id = repository.nextId();
        Team team = new Team(id, "Initiates", lead);
        team.addMember(new TeamMember(repository.nextMemberId(), member1, TeamMemberPosition.DEVOPS));
        team.addMember(new TeamMember(repository.nextMemberId(), member2, TeamMemberPosition.DEVELOPER));
        team.addMember(new TeamMember(repository.nextMemberId(), member3, TeamMemberPosition.QA_ENGINEER));

        repository.save(team);

        entityManager.flush();
        entityManager.clear();

        assertThat(repository.findById(id))
                .hasValueSatisfying(team1 -> {
                    assertThat(team1.getId()).isEqualTo(id);
                    assertThat(team1.getLead().getId()).isEqualTo(lead.getId());
                    assertThat(team1.getMembers()).hasSize(3);
                });
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> uniqueIdGenerator() {
            return new InMemoryUniqueIdGenerator();
        }
    }
}