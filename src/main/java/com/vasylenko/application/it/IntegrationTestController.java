package com.vasylenko.application.it;

import com.google.common.collect.ImmutableSortedSet;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.PhoneNumber;
import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.team.member.TeamMemberPosition;
import com.vasylenko.application.model.team.parameters.CreateTeamParameters;
import com.vasylenko.application.model.user.UserName;
import com.vasylenko.application.model.user.UserNameAndId;
import com.vasylenko.application.model.user.parameters.CreateUserParameters;
import com.vasylenko.application.service.TeamService;
import com.vasylenko.application.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

/**
 * REST controller for integration tests.
 */
@RestController
@RequestMapping("/api/integration-test")
@Profile("integration-test")
public class IntegrationTestController {

    private final UserService userService;
    private final TeamService teamService;

    public IntegrationTestController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    /**
     * Resets the database by deleting all teams and users, then adding a user and an administrator.
     */
    @PostMapping("/reset-db")
    public void resetDatabase() {
        teamService.deleteAllTeams();
        userService.deleteAllUsers();
        addUser();
        addAdministrator();
    }

    /**
     * Adds a test team with a developer.
     */
    @PostMapping("/add-test-team")
    public void addTestTeam() {
        ImmutableSortedSet<UserNameAndId> users = userService.getAllUsersNameAndId();
        UserNameAndId userNameAndId = users.first();

        teamService.createTeam(new CreateTeamParameters(
                "Test Team",
                userNameAndId.getId(),
                Set.of(new TeamMemberParameters(users.last().getId(),
                        TeamMemberPosition.DEVELOPER))));
    }

    /**
     * Adds a user to the database.
     */
    private void addUser() {
        userService.createUser(new CreateUserParameters(
                new UserName("User", "Last"),
                "user-pwd",
                Gender.MALE,
                LocalDate.parse("2010-04-13"),
                new Email("user.last@gmail.com"),
                new PhoneNumber("+555 789 456")));
    }

    /**
     * Adds an administrator to the database.
     */
    private void addAdministrator() {
        userService.createAdministrator(new CreateUserParameters(
                new UserName("Admin", "Strator"),
                "admin-pwd",
                Gender.FEMALE,
                LocalDate.parse("2008-09-25"),
                new Email("admin.strator@gmail.com"),
                new PhoneNumber("+555 123 456")));
    }
}
