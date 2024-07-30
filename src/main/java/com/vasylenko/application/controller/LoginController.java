package com.vasylenko.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
     * Displays the login page if the user is not authenticated, otherwise redirects to the home page.
     *
     * @param userDetails the authenticated user details
     * @return the view name for the login page or redirect to the home page
     */
    @GetMapping("/login")
    @Operation(summary = "Login Page", description = "Displays the login page if the user is not authenticated, otherwise redirects to the home page")
    public String login(@AuthenticationPrincipal UserDetails userDetails) {
        return (userDetails == null) ? "login" : "redirect:/";
    }
}
