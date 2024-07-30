package com.vasylenko.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Login Controller", description = "Controller for handling login operations")
public class LoginController {

    @GetMapping("/login")
    @Operation(summary = "Login Page", description = "Displays the login page if the user is not authenticated, otherwise redirects to the home page")
    public String login(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "login";
        } else {
            return "redirect:/";
        }
    }
}
