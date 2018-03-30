package com.epam.test_generator.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.test_generator.dto.CreateProjectDTO;
import com.epam.test_generator.dto.GetProjectFullDTO;
import com.epam.test_generator.services.ProjectService;
import com.epam.test_generator.services.exceptions.NotFoundException;
import com.epam.test_generator.services.exceptions.ProjectClosedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {
    @Test
    public void getUserProjects_CorrectRequest_StatusOk() throws Exception {
    }

    @Test
    public void getProject_CorrectProjectId_StatusOk() throws Exception {
    }

    @Test
    public void getProject_IncorrectProjectId_StatusNotFound() throws Exception {
    }

    @Test
    public void createProject_CorrectDTO_StatusCreated() throws Exception {
    }

    @Test
    public void createProject_IncorrectDTO_StatusBadRequest() throws Exception {
    }

    @Test
    public void updateProject_ValidInput_StatusOk() throws Exception {
    }

    @Test
    public void updateProject_InvalidInput_StatusBadRequest() throws Exception {
    }

    @Test
    public void updateProject_ValidUpdateDTO_StatusNotFound() throws Exception {
    }

    @Test
    public void updateProject_ValidUpdateDTO_StatusForbidden() throws Exception {
    }

    @Test
    public void closeProject_ProjectId_StatusOk() throws Exception {
    }

    @Test
    public void closeProject_ProjectId_StatusNotFound() throws Exception {
    }

    @Test
    public void closeProject_ProjectId_StatusForbidden() throws Exception {
    }

    @Test
    public void assignUserToProject_ValidInput_StatusOk() throws Exception {
    }

    @Test
    public void assignUserToProject_InvalidInput_StatusNotFound() throws Exception {
    }

    @Test
    public void assignUserToProject_ValidInput_StatusForbidden() throws Exception {
    }

    @Test
    public void removeUserFromProject_ValidInput_StatusOk() throws Exception {
    }

    @Test
    public void removeUserFromProject_InvalidInput_StatusNotFound() throws Exception {
    }

    @Test
    public void removeUserFromProject_ValidInput_StatusForbidden() throws Exception {
    }
}