package com.epam.test_generator.controllers;

import com.epam.test_generator.dto.ChangeUserRoleDTO;
import com.epam.test_generator.dto.JiraSettingsDTO;
import com.epam.test_generator.services.AdminService;
import com.epam.test_generator.services.JiraSettingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    private MockMvc mockMvc;
    private ChangeUserRoleDTO userChangeRole;
    private ObjectMapper mapper;
    @Mock
    private AdminService adminService;
    @Mock
    private JiraSettingsService jiraSettingsService;

    private JiraSettingsDTO jiraSettingsDTO;

    @InjectMocks
    private AdminController adminController;

    @Before

    public void setUp() {
        mapper = new ObjectMapper();
        userChangeRole = new ChangeUserRoleDTO();
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
            .setControllerAdvice(new GlobalExceptionController())
            .build();
    }

    @Test
    public void changeUserRole_SimpleRole_StatusOk() throws Exception {
        userChangeRole.setEmail("email@mail.com");
        userChangeRole.setRole("Role");
        final String json = mapper.writeValueAsString(userChangeRole);

        mockMvc.perform(
            put("/admin/changeroles").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void createJiraSettings_JiraSetting_StatusOk() throws Exception {
        jiraSettingsDTO = new JiraSettingsDTO();
        jiraSettingsDTO.setLogin("login");
        jiraSettingsDTO.setPassword("password");
        jiraSettingsDTO.setUri("uri");

        mockMvc.perform(post("/admin/jira_settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(jiraSettingsDTO)))
            .andExpect(status().isOk());

        verify(jiraSettingsService).createJiraSettings(eq(jiraSettingsDTO));
    }

    @Test
    public void getJiraSettings_JiraSettings_StatusOk() throws Exception {
        mockMvc.perform(get("/admin/jira_settings"))
            .andExpect(status().isOk());

        verify(jiraSettingsService).getJiraSettings();
    }
}