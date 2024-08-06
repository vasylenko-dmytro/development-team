package com.vasylenko.application.controller;

import com.vasylenko.application.config.SpringSecurityTestConfig;
import com.vasylenko.application.util.CustomLogger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.vasylenko.application.service.StubUserDetailsService.USERNAME_USER;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RootController.class)
@Import(SpringSecurityTestConfig.class)
class RootControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomLogger customLogger;

    @Test
    @WithUserDetails(USERNAME_USER)
    void testRootEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(customLogger).info("Displaying the Dashboard page");
    }
}