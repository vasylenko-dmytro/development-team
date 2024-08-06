package com.vasylenko.application.controller;

import com.vasylenko.application.config.SpringSecurityTestConfig;
import com.vasylenko.application.formdata.CreateUserFormData;
import com.vasylenko.application.formdata.EditUserFormData;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.PhoneNumber;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.model.user.UserName;
import com.vasylenko.application.service.UserService;
import com.vasylenko.application.util.CustomLogger;
import com.vasylenko.application.util.EditMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

import static com.vasylenko.application.service.StubUserDetailsService.USERNAME_ADMIN;
import static com.vasylenko.application.service.StubUserDetailsService.USERNAME_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
@Import(SpringSecurityTestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CustomLogger customLogger;

    private UserId id;
    private User user;

    @BeforeEach
    public void setUp() {
        id = new UserId(UUID.randomUUID());
        user = User.createUser(id,
                new UserName("Tommy", "Walton"),
                "encoded-secret-pwd",
                Gender.MALE,
                LocalDate.of(2001, Month.FEBRUARY, 17),
                new Email("tommy.walton@gmail.com"),
                new PhoneNumber("202 555 0192"));
    }
    @Test
    void testGetUsersRedirectsToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails(USERNAME_USER)
    void testGetUsersAsUser() throws Exception {

        when(userService.getUsers(any(Pageable.class)))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(USERNAME_ADMIN)
    void testCreateUserForm() throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/edit"))
                .andExpect(model().attributeExists("user", "editMode"));
    }

    @Test
    @WithUserDetails(USERNAME_USER)
    void testOpenCreateUserFormWithoutPermission() throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(USERNAME_USER)
    void testListUsers() throws Exception {
        when(userService.getUsers(any(Pageable.class))).thenReturn(Page.empty());

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithUserDetails(USERNAME_ADMIN)
    void testShowCreateUserForm() throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/edit"))
                .andExpect(model().attributeExists("user", "editMode"))
                .andExpect(model().attribute("editMode", EditMode.CREATE));
    }
}
