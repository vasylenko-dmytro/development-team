package com.vasylenko.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Login Controller.
 * <p> Handles login operations for the application. </p>
 */
@Controller
@Tag(name = "Login Controller", description = "Controller for handling login operations")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Displays the login page if the user is not authenticated, otherwise redirects to the home page.
     *
     * @param userDetails the authenticated user details
     * @return the view name for the login page or redirect to the home page
     */
    @GetMapping("/login")
    @Operation(summary = "Login Page", description = "Displays the login page if the user is not authenticated, otherwise redirects to the home page")
    public String login(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            logger.info("User is not authenticated, displaying login page");
            return "login";
        } else {
            logger.info("User is authenticated, redirecting to home page");
            return "redirect:/";
        }
    }
}
