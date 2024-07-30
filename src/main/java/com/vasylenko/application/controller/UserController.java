package com.vasylenko.application.controller;

import com.vasylenko.application.exception.UserNotFoundException;
import com.vasylenko.application.formdata.CreateUserFormData;
import com.vasylenko.application.formdata.EditUserFormData;
import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.model.user.UserRole;
import com.vasylenko.application.service.UserService;
import com.vasylenko.application.util.EditMode;
import com.vasylenko.application.validation.CreateUserValidationGroupSequence;
import com.vasylenko.application.validation.EditUserValidationGroupSequence;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @ModelAttribute("genders")
  public List<Gender> genders() {
    return List.of(Gender.MALE, Gender.FEMALE, Gender.OTHER);
  }

  @ModelAttribute("possibleRoles")
  public List<UserRole> possibleRoles() {
    return List.of(UserRole.values());
  }

  @GetMapping
  @Operation(summary = "Get list of users", description = "Returns a paginated list of users")
  public String index(Model model,
                      @SortDefault.SortDefaults({
                              @SortDefault("userName.lastName"),
                              @SortDefault("userName.firstName")}) Pageable pageable) {
    model.addAttribute("users", service.getUsers(pageable));
    return "users/list";
  }

  @GetMapping("/create")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Show create user form", description = "Displays the form to create a new user")
  public String createUserForm(Model model) {
    model.addAttribute("user", new CreateUserFormData());
    model.addAttribute("editMode", EditMode.CREATE);
    return "users/edit";
  }

  @PostMapping("/create")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Create a new user", description = "Creates a new user with the provided data")
  public String doCreateUser(@Validated(CreateUserValidationGroupSequence.class) @ModelAttribute("user") CreateUserFormData formData,
                             BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("editMode", EditMode.CREATE);
      return "users/edit";
    }
    service.createUser(formData.toParameters());
    return "redirect:/users";
  }

  @GetMapping("/{id}")
  @Operation(summary = "Show edit user form", description = "Displays the form to edit an existing user")
  public String editUserForm(@PathVariable("id") UserId userId,    
                             Model model) {
    User user = service.getUser(userId)
            .orElseThrow(() -> new UserNotFoundException(userId)); 
    model.addAttribute("user", EditUserFormData.fromUser(user)); 
    model.addAttribute("editMode", EditMode.UPDATE); 
    return "users/edit"; 
  }

  @PostMapping("/{id}")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Edit an existing user", description = "Updates an existing user with the provided data")
  public String doEditUser(@PathVariable("id") UserId userId,
                           @Validated(EditUserValidationGroupSequence.class) @ModelAttribute("user") EditUserFormData formData,    
                           BindingResult bindingResult,
                           Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("editMode", EditMode.UPDATE);
      return "users/edit";
    }
    service.editUser(userId, formData.toParameters());
    return "redirect:/users";
  }

  @PostMapping("/{id}/delete")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Delete a user", description = "Deletes an existing user by ID")
  public String doDeleteUser(@PathVariable("id") UserId userId,
                             RedirectAttributes redirectAttributes) {    
    User user = service.getUser(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));    

    service.deleteUser(userId);

    redirectAttributes.addFlashAttribute("deletedUserName",
            user.getUserName().getFullName());    

    return "redirect:/users";
  }
}
