package com.vasylenko.application.controller;

import com.vasylenko.application.exception.LogNotFoundException;
import com.vasylenko.application.model.document.LogEntry;
import com.vasylenko.application.service.impl.LogServiceImpl;
import com.vasylenko.application.util.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/logs")
public class LogViewerController {

    private final LogServiceImpl service;
    private final CustomLogger customLogger;

    @Autowired
    public LogViewerController(LogServiceImpl service, CustomLogger customLogger) {
        this.service = service;
        this.customLogger = customLogger;
    }

    @GetMapping
    public String listLogs(Model model, @SortDefault.SortDefaults({
            @SortDefault("userName.lastName"),
            @SortDefault("userName.firstName")}) Pageable pageable) {

        model.addAttribute("logs", service.getLogs(pageable));
        return "logs/list";
    }

    @PostMapping("/{id}/delete")
    @Secured("ROLE_ADMIN")
    public String deleteLog(@PathVariable("id") String logId, RedirectAttributes redirectAttributes) {
        customLogger.info(String.format("Deleting log with ID: %s", logId));

        LogEntry logEntry = service.getLog(logId)
                .orElseThrow(() -> new LogNotFoundException(logId));
        service.deleteLog(logId);
        redirectAttributes.addFlashAttribute("deletedUserName", logEntry.getId());

        customLogger.info("Log deleted successfully");
        return "redirect:/logs";
    }

    @PostMapping("/delete")
    @Secured("ROLE_ADMIN")
    public String deleteAllLogs(RedirectAttributes redirectAttributes) {
        service.deleteAllLogs();
        redirectAttributes.addFlashAttribute("deletedAllLogs", true);

        customLogger.info("Logs deleted successfully");
        return "redirect:/logs";
    }

}
