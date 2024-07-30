package com.vasylenko.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Tag(name = "Root Controller", description = "Controller for handling the root endpoint")
public class RootController {

    @GetMapping
    @Operation(summary = "Dashboard Page", description = "Displays the Dashboard page")
    public String root() {
        return "index";
    }
}
