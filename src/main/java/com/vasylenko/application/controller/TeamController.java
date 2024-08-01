package com.vasylenko.application.controller;

import com.vasylenko.application.exception.TeamNotFoundException;
import com.vasylenko.application.formdata.CreateTeamFormData;
import com.vasylenko.application.formdata.EditTeamFormData;
import com.vasylenko.application.formdata.TeamMemberFormData;
import com.vasylenko.application.model.team.Team;
import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.member.TeamMemberPosition;
import com.vasylenko.application.service.TeamService;
import com.vasylenko.application.service.UserService;
import com.vasylenko.application.util.CustomLogger;
import com.vasylenko.application.util.EditMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Team Controller.
 * <p> Handles team management operations for the application:
 * <ul><li>Get list of teams</li>
 * <li>Show create team form</li>
 * <li>Create a new team</li>
 * <li>Show edit team form</li>
 * <li>Edit an existing team</li>
 * <li>Delete a team</li>
 * <li>Get edit team member fragment</li></ul>
 * </p>
 */
@Controller
@RequestMapping("/teams")
@Tag(name = "Team Management", description = "Operations related to team management")
public class TeamController {

    private final TeamService service;
    private final UserService userService;
    private final CustomLogger customLogger;

    /**
     * Constructor-based dependency injection for the team controller.
     *
     * @param service the team service
     * @param userService the user service
     * @param customLogger the custom logger
     */
    @Autowired
    public TeamController(TeamService service, UserService userService, CustomLogger customLogger) {
        this.service = service;
        this.userService = userService;
        this.customLogger = customLogger;
    }

    /**
     * Initializes the data binder with custom validators.
     *
     * @param binder the data binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new RemoveUnusedTeamMembersValidator(binder.getValidator()));
    }


    /**
     * Returns a paginated list of teams.
     *
     * @param model the model
     * @param pageable the pagination information
     * @return the view name for the list of teams
     */
    @GetMapping
    @Operation(summary = "Get list of teams", description = "Returns a paginated list of teams")
    public String index(Model model, @SortDefault.SortDefaults(@SortDefault("name")) Pageable pageable) {
        customLogger.info("Fetching list of teams with pagination");

        model.addAttribute("teams", service.getTeams(pageable));
        return "teams/list";
    }

    /**
     * Displays the form to create a new team.
     *
     * @param model the model
     * @return the view name for the create team form
     */
    @GetMapping("/create")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Show create team form", description = "Displays the form to create a new team")
    public String createTeamForm(Model model) {
        customLogger.info("Displaying create team form");

        model.addAttribute("team", new CreateTeamFormData());
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", TeamMemberPosition.values());
        return "teams/edit";
    }

    /**
     * Creates a new team with the provided data.
     *
     * @param formData the form data
     * @param bindingResult the binding result
     * @param model the model
     * @return the view name for the list of teams or the create team form if there are errors
     */
    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Create a new team", description = "Creates a new team with the provided data")
    public String doCreateTeam(@Valid @ModelAttribute("team") CreateTeamFormData formData,
                               BindingResult bindingResult, Model model) {
        customLogger.info(String.format("Creating a new team with form data: %s", formData));

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.CREATE);
            model.addAttribute("users", userService.getAllUsersNameAndId());
            model.addAttribute("positions", TeamMemberPosition.values());
            return "teams/edit";
        }
        service.createTeam(formData.toParameters());
        customLogger.info("Team created successfully");

        return "redirect:/teams";
    }

    /**
     * Displays the form to edit an existing team.
     *
     * @param teamId the team ID
     * @param model the model
     * @return the view name for the edit team form
     */
    @GetMapping("/{id}")
    @Operation(summary = "Show edit team form", description = "Displays the form to edit an existing team")
    public String editTeamForm(@PathVariable("id") TeamId teamId, Model model) {
        customLogger.info(String.format("Displaying edit team form for team ID: %s", teamId));

        Team team = service.getTeamWithMembers(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        model.addAttribute("team", EditTeamFormData.fromTeam(team));
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", TeamMemberPosition.values());
        model.addAttribute("editMode", EditMode.UPDATE);
        return "teams/edit";
    }

    /**
     * Updates an existing team with the provided data.
     *
     * @param teamId the team ID
     * @param formData the form data
     * @param bindingResult the binding result
     * @param model the model
     * @return the view name for the list of teams or the edit team form if there are errors
     */
    @PostMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Edit an existing team", description = "Updates an existing team with the provided data")
    public String doEditTeam(@PathVariable("id") TeamId teamId,
                             @Valid @ModelAttribute("team") EditTeamFormData formData,
                             BindingResult bindingResult,
                             Model model) {
        customLogger.info(String.format("Editing team with ID: %s and form data: %s", teamId, formData));

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.UPDATE);
            model.addAttribute("users", userService.getAllUsersNameAndId());
            model.addAttribute("positions", TeamMemberPosition.values());
            return "teams/edit";
        }
        service.editTeam(teamId, formData.toParameters());
        customLogger.info("Team updated successfully");

        return "redirect:/teams";
    }

    /**
     * Deletes an existing team by ID.
     *
     * @param teamId the team ID
     * @param redirectAttributes the redirect attributes
     * @return the view name for the list of teams
     */
    @PostMapping("/{id}/delete")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Delete a team", description = "Deletes an existing team by ID")
    public String doDeleteTeam(@PathVariable("id") TeamId teamId,
                               RedirectAttributes redirectAttributes) {
        customLogger.info(String.format("Deleting team with ID: %s", teamId));

        Team team = service.getTeam(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        service.deleteTeam(teamId);
        redirectAttributes.addFlashAttribute("deletedTeamName", team.getName());

        customLogger.info("Team deleted successfully");
        return "redirect:/teams";
    }

    /**
     * Returns the fragment to edit a team member.
     *
     * @param model the model
     * @param index the index of the team member
     * @return the view name for the edit team member fragment
     */
    @GetMapping("/edit-team-member")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Get edit team member fragment", description = "Returns the fragment to edit a team member")
    public String getEditTeamMemberFragment(Model model, @RequestParam("index") int index) {
        customLogger.info(String.format("Fetching edit team member fragment for index: %s", index));

        model.addAttribute("index", index);
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", TeamMemberPosition.values());
        model.addAttribute("teamObjectName", "dummyTeam");
        model.addAttribute("dummyTeam", new DummyTeamForTeamMemberFragment());
        return "teams/edit-team-member :: teammember-form";
    }

    /**
     * Dummy team class for team member fragment.
     */
    @Setter
    @Getter
    private static class DummyTeamForTeamMemberFragment {
        private TeamMemberFormData[] members;

    }

    /**
     * Validator to remove unused team members.
     */
    private record RemoveUnusedTeamMembersValidator(Validator validator) implements Validator {
        @Override
        public boolean supports(@Nonnull Class<?> clazz) {
            return validator.supports(clazz);
        }
        @Override
        public void validate(@Nonnull Object target, @Nonnull Errors errors) {
            if (target instanceof CreateTeamFormData formData) {
                formData.removeEmptyTeamMemberForms();
            }
            validator.validate(target, errors);
        }
    }
}
