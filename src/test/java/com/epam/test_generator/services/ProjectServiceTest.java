package com.epam.test_generator.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

import com.epam.test_generator.services.exceptions.BadRequestException;
import com.epam.test_generator.services.exceptions.NotFoundException;
import com.epam.test_generator.services.exceptions.ProjectClosedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    @Test
    public void get_AllProjects_Success() {
    }

    @Test
    public void get_AuthUserProjects_Success() {
    }

    @Test(expected = NotFoundException.class)
    public void get_AuthUserProjects_NotFoundException() {
    }

    @Test
    public void getProjects_ByUserId_Success() {
    }

    @Test(expected = NotFoundException.class)
    public void getProjectsByUserId_InvalidUserId_NotFoundException() {
    }

    @Test
    public void getProjectByProjectId_ValidProjectId_Success() {
    }

    @Test(expected = NotFoundException.class)
    public void getProjectByProjectId_InvalidProjectId_NotFoundException() {
    }

    @Test
    public void getAuthUserFullProject_ProjectIdAndAuth_Success() {
    }

    @Test(expected = BadRequestException.class)
    public void getAuthUserFullProject_UserDoesNotBelongsToSuit_BadRequestException() {
    }

    @Test
    public void create_Project_Success() {
    }

    @Test
    public void update_Project_Success() {
    }

    @Test(expected = NotFoundException.class)
    public void updateProject_InvalidProjectId_NotFound() {
    }

    @Test
    public void remove_Project_Success() {
    }

    @Test(expected = NotFoundException.class)
    public void removeProject_InvalidProjectId_NotFound() {
    }

    @Test
    public void addUserToProject_ValidUser_Success() {
    }

    @Test(expected = NotFoundException.class)
    public void addUserToProject_InvalidProjectId_NotFound() {
    }

    @Test(expected = NotFoundException.class)
    public void addUserToProject_InvalidUserId_NotFound() {
    }

    @Test
    public void removeUserFromProject_ValidUser_Success() {
    }

    @Test (expected = NotFoundException.class)
    public void removeUserFromProject_InvalidProjectId_NotFound() {
    }

    @Test (expected = NotFoundException.class)
    public void removeUserFromProject_InvalidUserId_NotFound() {
    }

    @Test
    public void closeProject_ValidProjectId_Success() {
    }

    @Test(expected = NotFoundException.class)
    public void closeProject_InvalidProjectId_NotFound() {
    }

    @Test(expected = ProjectClosedException.class)
    public void closeProject_ProjectIsClosed_FailReadOnly() {
    }
}