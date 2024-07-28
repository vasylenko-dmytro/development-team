package com.vasylenko.application.controller;

import com.vasylenko.application.config.SpringSecurityTestConfig;
import com.vasylenko.application.service.TeamService;
import com.vasylenko.application.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.vasylenko.application.service.StubUserDetailsService.USERNAME_ADMIN;
import static com.vasylenko.application.service.StubUserDetailsService.USERNAME_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TeamController.class)
@Import(SpringSecurityTestConfig.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeamService teamService;
    @MockBean
    private UserService userService;

    @Test
    @WithUserDetails(USERNAME_ADMIN)
    void testIndex() throws Exception {
        when(teamService.getTeams(any(Pageable.class))).thenReturn(Page.empty());
        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(view().name("teams/list"))
                .andExpect(model().attributeExists("teams"));
    }

    @Test
    @WithUserDetails(USERNAME_USER)
    void testGetTeamsAsUser() throws Exception {
        when(teamService.getTeams(any(Pageable.class)))
                .thenReturn(Page.empty());
        mockMvc.perform(get("/teams"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(USERNAME_ADMIN)
    void testCreateTeamForm() throws Exception {
        mockMvc.perform(get("/teams/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("teams/edit"))
                .andExpect(model().attributeExists("team", "positions"));
    }

    @Test
    @WithUserDetails(USERNAME_USER)
    void testOpenCreateTeamFormWithoutPermission() throws Exception {
        mockMvc.perform(get("/teams/create"))
                .andExpect(status().isForbidden());
    }
}
