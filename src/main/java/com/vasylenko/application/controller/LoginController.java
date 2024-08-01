package com.vasylenko.application.controller;

import com.vasylenko.application.util.CustomLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
    * Custom logger for the login controller.
    */
    private final CustomLogger customLogger;

    /**
     * Constructor-based dependency injection for the login controller.
     */
    @Autowired
    public LoginController(CustomLogger customLogger) {
        this.customLogger = customLogger;
    }

    /**
     * Displays the login page if the user is not authenticated, otherwise redirects to the home page.
     *
     * @return the login page or a redirect to the home page
     */
    @GetMapping("/login")
    @Operation(summary = "Login Page", description = "Displays the login page if the user is not authenticated, otherwise redirects to the home page")
    public String login(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            customLogger.info("User is not logged in yet - displaying login page");
            return "login";
        } else {
            customLogger.info("User already logged on, redirecting to home page");
            return "redirect:/";
        }
    }
}
