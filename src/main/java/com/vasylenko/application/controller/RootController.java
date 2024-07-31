package com.vasylenko.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    /**
     * Displays the Dashboard page.
     *
     * @return the view name for the Dashboard page
     */
    @GetMapping
    @Operation(summary = "Dashboard Page", description = "Displays the Dashboard page")
    public String root() {
        logger.info("Displaying the Dashboard page");
        return "index";
    }
}
