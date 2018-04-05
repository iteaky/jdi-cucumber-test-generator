package com.epam.test_generator.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.test_generator.controllers.Admin.request.JiraSettingsCreateDTO;
import com.epam.test_generator.dao.interfaces.JiraSettingsDAO;
import com.epam.test_generator.entities.JiraSettings;
import com.epam.test_generator.services.exceptions.JiraAuthenticationException;
import com.epam.test_generator.services.exceptions.NotFoundException;

import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JiraSettingsServiceTest {

    @Mock
    private JiraSettingsDAO jiraSettingsDAO;

    @InjectMocks
    private JiraSettingsService jiraSettingsService;

    private JiraSettingsCreateDTO jiraSettingsCreateDTO;

    private static final Long JIRA_SETTINGS_ID = 1L;
    private static final String JIRA_URI = "uri";
    private static final String JIRA_LOGIN = "login";
    private static final String JIRA_PASSWORD = "password";

    @Before
    public void setUp() throws Exception {
        jiraSettingsCreateDTO = new JiraSettingsCreateDTO();
    }

    @Test
    public void createJiraSettings_CorrectData_Success() {
        when(jiraSettingsDAO.findByLogin(anyString())).thenReturn(null);
        jiraSettingsCreateDTO.setLogin(JIRA_LOGIN);
        jiraSettingsCreateDTO.setPassword(JIRA_PASSWORD);
        jiraSettingsCreateDTO.setUri(JIRA_URI);

        JiraSettings expectedJiraSettings = new JiraSettings(jiraSettingsCreateDTO.getUri(),
            jiraSettingsCreateDTO.getLogin(), jiraSettingsCreateDTO.getPassword());
        JiraSettings createdJiraSettings = jiraSettingsService.createJiraSettings(jiraSettingsCreateDTO);

        verify(jiraSettingsDAO).save(eq(expectedJiraSettings));
        Assert.assertEquals(expectedJiraSettings, createdJiraSettings);
    }

    @Test(expected = JiraAuthenticationException.class)
    public void createJiraSettings_ExistedData_Exception() {
        jiraSettingsCreateDTO.setLogin(JIRA_LOGIN);
        jiraSettingsCreateDTO.setPassword(JIRA_PASSWORD);
        jiraSettingsCreateDTO.setUri(JIRA_URI);

        JiraSettings existedJiraSettings = new JiraSettings(jiraSettingsCreateDTO.getUri(),
            jiraSettingsCreateDTO.getLogin(), jiraSettingsCreateDTO.getPassword());

        when(jiraSettingsDAO.findByLogin(anyString())).thenReturn(existedJiraSettings);
        jiraSettingsService.createJiraSettings(jiraSettingsCreateDTO);
    }

    @Test
    public void updateJiraSettings_CorrectData_Success() {
        jiraSettingsCreateDTO.setLogin("new_login");
        jiraSettingsCreateDTO.setPassword("new_password");
        jiraSettingsCreateDTO.setUri("new_uri");

        JiraSettings existedJiraSettings = new JiraSettings(JIRA_URI, JIRA_LOGIN, JIRA_PASSWORD);
        when(jiraSettingsDAO.findById(anyLong())).thenReturn(existedJiraSettings);

        jiraSettingsService.updateJiraSettings(JIRA_SETTINGS_ID, jiraSettingsCreateDTO);

        JiraSettings expectedJiraSettings = new JiraSettings(jiraSettingsCreateDTO.getUri(),
            jiraSettingsCreateDTO.getLogin(), jiraSettingsCreateDTO.getPassword());

        verify(jiraSettingsDAO).save(eq(expectedJiraSettings));
    }

    @Test(expected = NotFoundException.class)
    public void updateJiraSettings_WrongId_Exception() {

        when(jiraSettingsDAO.findById(anyLong())).thenReturn(null);
        jiraSettingsService.updateJiraSettings(JIRA_SETTINGS_ID, jiraSettingsCreateDTO);
    }

    @Test
    public void getJiraSettings() {

        JiraSettings existedJiraSettings = new JiraSettings(JIRA_URI, JIRA_LOGIN, JIRA_PASSWORD);
        when(jiraSettingsDAO.findAll()).thenReturn(Collections.singletonList(existedJiraSettings));

        List<JiraSettings> jiraSettingsList = jiraSettingsService.getJiraSettings();
        Assert.assertTrue(!jiraSettingsList.isEmpty());
    }
}