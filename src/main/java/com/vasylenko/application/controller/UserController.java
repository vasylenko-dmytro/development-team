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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * User Controller.
 * <p> Handles user management operations for the application:
 * <ul><li>Get list of users</li>
 * <li>Show create user form</li>
 * <li>Create a new user</li>
 * <li>Show edit user form</li>
 * <li>Edit an existing user</li>
 * <li>Delete a user</li></ul>
 * </p>
 */
@Controller
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;

    logger.info("UserController initialized with UserService");

  }

  /**
   * Validator to remove unused team members.
   */
  @ModelAttribute("genders")
  public List<Gender> genders() {
    logger.info("Adding genders to model attributes");

    return List.of(Gender.MALE, Gender.FEMALE, Gender.OTHER);
  }

  /**
   * Provides a list of possible user roles for the model.
   *
   * @return the list of user roles
   */
  @ModelAttribute("possibleRoles")
  public List<UserRole> possibleRoles() {
    logger.info("Adding possible user roles to model attributes");

    return List.of(UserRole.values());
  }

  /**
   * Returns a paginated list of users.
   *
   * @param model the model
   * @param pageable the pagination information
   * @return the view name for the list of users
   */
  @GetMapping
  @Operation(summary = "Get list of users", description = "Returns a paginated list of users")
  public String listUsers(Model model, @SortDefault.SortDefaults({
          @SortDefault("userName.lastName"),
          @SortDefault("userName.firstName")}) Pageable pageable) {
    logger.info("Fetching list of users with pagination");

    model.addAttribute("users", service.getUsers(pageable));
    return "users/list";
  }

  /**
   * Displays the form to create a new user.
   *
   * @param model the model
   * @return the view name for the create user form
   */
  @GetMapping("/create")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Show create user form", description = "Displays the form to create a new user")
  public String showCreateUserForm(Model model) {
    logger.info("Displaying create user form");

    model.addAttribute("user", new CreateUserFormData());
    model.addAttribute("editMode", EditMode.CREATE);
    return "users/edit";
  }

  /**
   * Creates a new user with the provided data.
   *
   * @param formData the form data
   * @param bindingResult the binding result
   * @param model the model
   * @return the view name for the list of users or the create user form if there are errors
   */
  @PostMapping("/create")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Create a new user", description = "Creates a new user with the provided data")
  public String createUser(@Validated(CreateUserValidationGroupSequence.class) @ModelAttribute("user") CreateUserFormData formData,
                             BindingResult bindingResult, Model model) {
    logger.info("Creating a new user with form data: {}", formData);

    if (bindingResult.hasErrors()) {
      logger.warn("Validation errors while creating a new user: {}", bindingResult.getAllErrors());

      model.addAttribute("editMode", EditMode.CREATE);
      return "users/edit";
    }
    service.createUser(formData.toParameters());

    logger.info("User created successfully");
    return "redirect:/users";
  }

  /**
   * Displays the form to edit an existing user.
   *
   * @param userId the user ID
   * @param model the model
   * @return the view name for the edit user form
   */
  @GetMapping("/{id}")
  @Operation(summary = "Show edit user form", description = "Displays the form to edit an existing user")
  public String showEditUserForm(@PathVariable("id") UserId userId, Model model) {
    logger.info("Displaying edit user form for user ID: {}", userId);

    User user = service.getUser(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    model.addAttribute("user", EditUserFormData.fromUser(user));
    model.addAttribute("editMode", EditMode.UPDATE);
    return "users/edit";
  }

  /**
   * Updates an existing user with the provided data.
   *
   * @param userId the user ID
   * @param formData the form data
   * @param bindingResult the binding result
   * @param model the model
   * @return the view name for the list of users or the edit user form if there are errors
   */
  @PostMapping("/{id}")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Edit an existing user", description = "Updates an existing user with the provided data")
  public String editUser(@PathVariable("id") UserId userId,
                         @Validated(EditUserValidationGroupSequence.class) @ModelAttribute("user") EditUserFormData formData,
                         BindingResult bindingResult,
                         Model model) {
    logger.info("Editing user with ID: {} and form data: {}", userId, formData);

    if (bindingResult.hasErrors()) {
      logger.warn("Validation errors while editing user: {}", bindingResult.getAllErrors());

      model.addAttribute("editMode", EditMode.UPDATE);
      return "users/edit";
    }
    service.editUser(userId, formData.toParameters());

    logger.info("User updated successfully");
    return "redirect:/users";
  }


  /**
   * Deletes an existing user by ID.
   *
   * @param userId the user ID
   * @param redirectAttributes the redirect attributes
   * @return the view name for the list of users
   */
  @PostMapping("/{id}/delete")
  @Secured("ROLE_ADMIN")
  @Operation(summary = "Delete a user", description = "Deletes an existing user by ID")
  public String deleteUser(@PathVariable("id") UserId userId, RedirectAttributes redirectAttributes) {
    logger.info("Deleting user with ID: {}", userId);

    User user = service.getUser(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    service.deleteUser(userId);
    redirectAttributes.addFlashAttribute("deletedUserName", user.getUserName().getFullName());

    logger.info("User deleted successfully");
    return "redirect:/users";
  }
}
