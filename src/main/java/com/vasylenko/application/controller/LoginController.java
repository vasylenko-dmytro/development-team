package com.vasylenko.application.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "login";
        } else {
            return "redirect:/";
        }
    }
}
