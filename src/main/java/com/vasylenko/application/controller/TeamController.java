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
import com.vasylenko.application.util.EditMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
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

@Controller
@RequestMapping("/teams")
@Tag(name = "Team Management", description = "Operations related to team management")
public class TeamController {

    private final TeamService service;
    private final UserService userService;

    public TeamController(TeamService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new RemoveUnusedTeamMembersValidator(binder.getValidator()));
    }

    @GetMapping
    @Operation(summary = "Get list of teams", description = "Returns a paginated list of teams")
    public String index(Model model, @SortDefault.SortDefaults(@SortDefault("name")) Pageable pageable) {
        model.addAttribute("teams", service.getTeams(pageable));
        return "teams/list";
    }

    @GetMapping("/create")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Show create team form", description = "Displays the form to create a new team")
    public String createTeamForm(Model model) {
        model.addAttribute("team", new CreateTeamFormData());
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", TeamMemberPosition.values());
        return "teams/edit";
    }

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Create a new team", description = "Creates a new team with the provided data")
    public String doCreateTeam(@Valid @ModelAttribute("team") CreateTeamFormData formData,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.CREATE);
            model.addAttribute("users", userService.getAllUsersNameAndId());
            model.addAttribute("positions", TeamMemberPosition.values());
            return "teams/edit";
        }

        service.createTeam(formData.toParameters());

        return "redirect:/teams";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Show edit team form", description = "Displays the form to edit an existing team")
    public String editTeamForm(@PathVariable("id") TeamId teamId, Model model) {
        Team team = service.getTeamWithMembers(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
        model.addAttribute("team", EditTeamFormData.fromTeam(team));
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", TeamMemberPosition.values());
        model.addAttribute("editMode", EditMode.UPDATE);
        return "teams/edit";
    }

    @PostMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Edit an existing team", description = "Updates an existing team with the provided data")
    public String doEditTeam(@PathVariable("id") TeamId teamId,
                             @Valid @ModelAttribute("team") EditTeamFormData formData,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.UPDATE);
            model.addAttribute("users", userService.getAllUsersNameAndId());
            model.addAttribute("positions", TeamMemberPosition.values());
            return "teams/edit";
        }

        service.editTeam(teamId, formData.toParameters());

        return "redirect:/teams";
    }

    @PostMapping("/{id}/delete")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Delete a team", description = "Deletes an existing team by ID")
    public String doDeleteTeam(@PathVariable("id") TeamId teamId,
                               RedirectAttributes redirectAttributes) {
        Team team = service.getTeam(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        service.deleteTeam(teamId);

        redirectAttributes.addFlashAttribute("deletedTeamName", team.getName());

        return "redirect:/teams";
    }

    @GetMapping("/edit-team-member")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Get edit team member fragment", description = "Returns the fragment to edit a team member")
    public String getEditTeamMemberFragment(Model model, @RequestParam("index") int index) {
        model.addAttribute("index", index);
        model.addAttribute("users", userService.getAllUsersNameAndId());
        model.addAttribute("positions", TeamMemberPosition.values());
        model.addAttribute("teamObjectName", "dummyTeam");
        model.addAttribute("dummyTeam", new DummyTeamForTeamMemberFragment());
        return "teams/edit-team-member :: teammember-form";
    }

    private static class DummyTeamForTeamMemberFragment {
        private TeamMemberFormData[] members;

        public TeamMemberFormData[] getMembers() {
            return members;
        }

        public void setMembers(TeamMemberFormData[] members) {
            this.members = members;
        }
    }
    private static class RemoveUnusedTeamMembersValidator implements Validator {
        private final Validator validator;

        private RemoveUnusedTeamMembersValidator(Validator validator) {
            this.validator = validator;
        }

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
