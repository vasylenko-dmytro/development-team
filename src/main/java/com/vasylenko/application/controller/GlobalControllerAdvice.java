package com.vasylenko.application.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global controller advice.
 * <p> Provides global controller advice for the application. </p>
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @Value("${application.version}") 
    private String version;

    /**
     * Adds the application version to the model attributes.
     *
     * @return the application version
     */
    @ModelAttribute("version") 
    public String getVersion() {
        return version;
    }

    /**
     * Handles DataIntegrityViolationException and
     * ObjectOptimisticLockingFailureException.
     *
     * @param request the HTTP request
     * @param e the exception
     * @return the model and view for the error page
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DataIntegrityViolationException.class, ObjectOptimisticLockingFailureException.class})
    public ModelAndView handleConflict(HttpServletRequest request, Exception e) {
        logger.error("Handling conflict for URL: {} with exception: {}", request.getRequestURL(), e.getMessage());

        ModelAndView result = new ModelAndView("error/409");
        result.addObject("url", request.getRequestURL());
        return result;
    }

    /**
     * Initializes the data binder with custom editors.
     *
     * @param binder the data binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logger.info("Initializing data binder with custom editors");

        StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(false);
        binder.registerCustomEditor(String.class, stringTrimmer);
    }
}
