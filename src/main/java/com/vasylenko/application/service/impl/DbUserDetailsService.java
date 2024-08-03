package com.vasylenko.application.service.impl;

import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.repository.UserRepository;
import com.vasylenko.application.util.CustomLogger;
import com.vasylenko.application.validation.ApplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

/**
 * Service for loading user-specific data.
 */
@Service
@Transactional(readOnly = true)
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomLogger customLogger;

    @Autowired
    public DbUserDetailsService(UserRepository userRepository, CustomLogger customLogger) {
        this.userRepository = userRepository;
        this.customLogger = customLogger;
    }

    /**
     * Loads the user by username (email).
     *
     * @param username the username (email) of the user
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        customLogger.info(String.format("Loading user by username: %s", username));
        User user = userRepository.findByEmail(new Email(username))
                .orElseThrow(() -> {
                    customLogger.warn(String.format("User with email %s could not be found", username));
                    return new UsernameNotFoundException(
                            format("User with email %s could not be found", username));
                });
        customLogger.info(String.format("User with email %s found", username));
        return new ApplicationUserDetails(user);
    }
}
