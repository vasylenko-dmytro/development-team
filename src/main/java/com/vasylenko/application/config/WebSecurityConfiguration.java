package com.vasylenko.application.config;

import com.vasylenko.application.util.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Web Security configuration.
 * <p> Configures the application with the following beans:
 * <ul> <li> An authentication provider using DAO authentication. </li>
 * <li> A security filter chain for HTTP security configuration. </li></ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final CustomLogger customLogger;

    @Autowired
    public WebSecurityConfiguration(PasswordEncoder passwordEncoder,
                                    UserDetailsService userDetailsService,
                                    CustomLogger customLogger) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.customLogger = customLogger;
    }

    /**
     * Configures the authentication provider with user details service and password encoder.
     *
     * @return the configured authentication provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        customLogger.info("Configuring AuthenticationProvider with UserDetailsService and PasswordEncoder");

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }


    /**
     * Configures the security filter chain with custom HTTP security settings.
     *
     * @param http the HTTP security builder
     * @return the configured security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        customLogger.info("Configuring SecurityFilterChain");

        http.csrf(httpSecurityCsrfConfigurer ->
                httpSecurityCsrfConfigurer.ignoringRequestMatchers("/api/integration-test/**"));
        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/api/integration-test/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/svg/*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(LogoutConfigurer::permitAll);

        customLogger.info("SecurityFilterChain configured successfully");
        return http.build();
    }
}
