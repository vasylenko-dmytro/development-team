package com.vasylenko.application.util;

import com.google.common.collect.Streams;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.PhoneNumber;
import com.vasylenko.application.model.team.member.TeamMemberParameters;
import com.vasylenko.application.model.team.member.TeamMemberPosition;
import com.vasylenko.application.model.team.parameters.CreateTeamParameters;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.model.user.parameters.CreateUserParameters;
import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserName;
import com.vasylenko.application.service.TeamService;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import com.vasylenko.application.service.UserService;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * DataInitializer is responsible for initializing the database with sample data.
 */
@Component
@Profile("init-db")
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private static final String[] TEAM_NAMES = new String[]{
            "Blackbirds",
            "Phoenixes",
            "Timberwolves",
            "Wolfs",
            "Planets"
    };

    private final Faker faker = new Faker();
    private final UserService userService;
    private final TeamService teamService;

    public DataInitializer(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    /**
     * Runs the data initialization process.
     *
     * @param args command line arguments
     */
    @Override
    public void run(String... args) {
        logger.info("Starting data initialization process");

        Set<User> generatedUsers = generateUsers();
        createAdministrator();

        Streams.forEachPair(generatedUsers.stream().limit(TEAM_NAMES.length),
                Arrays.stream(TEAM_NAMES),
                (user, teamName) -> createTeam(user, teamName, generatedUsers));

        logger.info("Data initialization process completed");
    }

    /**
     * Generates a set of random users.
     *
     * @return a set of generated users
     */
    private Set<User> generateUsers() {
        logger.info("Generating random users");

        Set<User> users = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            CreateUserParameters parameters = newRandomUserParameters();
            users.add(userService.createUser(parameters));

            logger.debug("Generated user: {}", parameters.getUserName().getFullName());
        }
        logger.info("Generated {} users", users.size());
        return users;
    }

    /**
     * Creates an administrator user.
     */
    private void createAdministrator() {
        logger.info("Creating administrator user");

        UserName userName = randomUserName();
        CreateUserParameters parameters = new CreateUserParameters(userName,
                userName.getFirstName(),
                randomGender(),
                LocalDate.parse("2000-01-01"),
                generateEmailForUserName(userName),
                randomPhoneNumber());
        userService.createAdministrator(parameters);

        logger.info("Administrator user created: {}", userName.getFullName());
    }

    /**
     * Creates a team with the given user as the team lead.
     *
     * @param user the team lead
     * @param teamName the name of the team
     * @param generatedUsers the set of generated users
     */
    private void createTeam(User user, String teamName, Set<User> generatedUsers) {
        logger.info("Creating team: {} with lead: {}", teamName, user.getUserName().getFullName());

        Set<TeamMemberParameters> members = Set.of(
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.BUSINESS_ANALYST),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.PRODUCT_OWNER),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.PROJECT_MANAGER),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.SOFTWARE_ARCHITECT),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.SCRUM_MASTER),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.DEVELOPER),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.QA_ENGINEER),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.DEVOPS),
                new TeamMemberParameters(randomUser(generatedUsers), TeamMemberPosition.UX_UI_DESIGNER)
        );
        CreateTeamParameters teamParameters = new CreateTeamParameters(teamName, user.getId(), members);
        teamService.createTeam(teamParameters);

        logger.info("Team created: {}", teamName);
    }

    /**
     * Selects a random user from the set of users.
     *
     * @param users the set of users
     * @return the ID of the selected user
     */
    private UserId randomUser(Set<User> users) {
        int index = faker.random().nextInt(users.size());
        Iterator<User> iter = users.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        UserId userId = iter.next().getId();

        logger.debug("Selected random user: {}", userId);
        return userId;
    }

    /**
     * Generates random parameters for creating a new user.
     *
     * @return the generated parameters
     */
    private CreateUserParameters newRandomUserParameters() {
        UserName userName = randomUserName();
        Gender gender = randomGender();
        // TODO: 'net.datafaker.providers.base.DateAndTime' is deprecated since version 2.3.0 and marked for removal
        LocalDate birthday = LocalDate.ofInstant(faker.date().birthday(10, 40).toInstant(), ZoneId.systemDefault());
        Email email = generateEmailForUserName(userName);
        PhoneNumber phoneNumber = randomPhoneNumber();
        CreateUserParameters parameters = new CreateUserParameters(userName, userName.getFirstName(), gender, birthday, email, phoneNumber);

        logger.debug("Generated random user parameters: {}", parameters);
        return parameters;
    }

    @Nonnull
    private UserName randomUserName() {
        Name name = faker.name();
        UserName userName = new UserName(name.firstName(), name.lastName());

        logger.debug("Generated random user name: {}", userName);
        return userName;
    }

    @Nonnull
    private PhoneNumber randomPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber(faker.phoneNumber().phoneNumber());

        logger.debug("Generated random phone number: {}", phoneNumber);
        return phoneNumber;
    }

    @Nonnull
    private Email generateEmailForUserName(UserName userName) {
        Email email = new Email(faker.internet().emailAddress(generateEmailLocalPart(userName)));

        logger.debug("Generated email for user name {}: {}", userName, email);
        return email;
    }

    @Nonnull
    private Gender randomGender() {
        Gender gender = faker.bool().bool() ? Gender.MALE : Gender.FEMALE;

        logger.debug("Generated random gender: {}", gender);
        return gender;
    }

    @Nonnull
    private String generateEmailLocalPart(UserName userName) {
        String localPart = String.format("%s.%s",
                StringUtils.remove(userName.getFirstName().toLowerCase(), "'"),
                StringUtils.remove(userName.getLastName().toLowerCase(), "'"));

        logger.debug("Generated email local part for user name {}: {}", userName, localPart);
        return localPart;
    }
}
