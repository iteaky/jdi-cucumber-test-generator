package com.epam.test_generator.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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