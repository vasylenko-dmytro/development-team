package com.vasylenko.application.controller;

import com.vasylenko.application.util.CustomLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Root Controller.
 * <p> Handles the root endpoint of the application. </p>
 */
@Controller
@RequestMapping("/")
@Tag(name = "Root Controller", description = "Controller for handling the root endpoint")
public class RootController {

    /**
     * Custom logger for the login controller.
     */
    private final CustomLogger customLogger;

    /**
     * Constructor-based dependency injection for the login controller.
     */
    @Autowired
    public RootController(CustomLogger customLogger) {
        this.customLogger = customLogger;
    }

    /**
     * Displays the Dashboard page.
     *
     * @return the view name for the Dashboard page
     */
    @GetMapping
    @Operation(summary = "Dashboard Page", description = "Displays the Dashboard page")
    public String root() {
        customLogger.info("Displaying the Dashboard page");
        return "index";
    }
}
