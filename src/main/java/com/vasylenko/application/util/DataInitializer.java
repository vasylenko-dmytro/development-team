package com.vasylenko.application.util;

import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.phone.PhoneNumber;
import com.vasylenko.application.model.user.CreateUserParameters;
import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.user.UserName;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import com.vasylenko.application.service.UserService;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@Profile("init-db")
public class DataInitializer implements CommandLineRunner {
    private final Faker faker = new Faker();
    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < 20; i++) {
            CreateUserParameters parameters = newRandomUserParameters();
            userService.createUser(parameters);
        }

        UserName userName = randomUserName();
        CreateUserParameters parameters = new CreateUserParameters(userName,
                userName.getFirstName(),
                randomGender(),
                LocalDate.parse("2000-01-01"),
                generateEmailForUserName(userName),
                randomPhoneNumber());
        userService.createAdministrator(parameters);
    }

    private CreateUserParameters newRandomUserParameters() {
        UserName userName = randomUserName();
        Gender gender = randomGender();
        // TODO: 'net.datafaker.providers.base.DateAndTime' is deprecated since version 2.3.0 and marked for removal
        LocalDate birthday = LocalDate.ofInstant((faker.date().birthday(10, 40)).toInstant(), ZoneId.systemDefault());
        Email email = generateEmailForUserName(userName);
        PhoneNumber phoneNumber = randomPhoneNumber();
        return new CreateUserParameters(userName, userName.getFirstName(), gender, birthday, email, phoneNumber);
    }

    @Nonnull
    private UserName randomUserName() {
        Name name = faker.name();
        return new UserName(name.firstName(), name.lastName());
    }

    @Nonnull
    private PhoneNumber randomPhoneNumber() {
        return new PhoneNumber(faker.phoneNumber().phoneNumber());
    }

    @Nonnull
    private Email generateEmailForUserName(UserName userName) {
        return new Email(faker.internet().emailAddress(generateEmailLocalPart(userName)));
    }

    @Nonnull
    private Gender randomGender() {
        return faker.bool().bool() ? Gender.MALE : Gender.FEMALE;
    }

    @Nonnull
    private String generateEmailLocalPart(UserName userName) {
        return String.format("%s.%s",
                StringUtils.remove(userName.getFirstName().toLowerCase(), "'"),
                StringUtils.remove(userName.getLastName().toLowerCase(), "'"));
    }
}
