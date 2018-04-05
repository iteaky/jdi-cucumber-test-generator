package com.epam.test_generator.services;

import com.epam.test_generator.config.security.AuthenticatedUser;
import com.epam.test_generator.controllers.Project.ProjectTransformer;
import com.epam.test_generator.controllers.Project.request.ProjectCreateDTO;
import com.epam.test_generator.controllers.Project.request.ProjectUpdateDTO;
import com.epam.test_generator.dao.interfaces.ProjectDAO;
import com.epam.test_generator.controllers.Project.responce.ProjectDTO;
import com.epam.test_generator.controllers.Project.responce.ProjectFullDTO;
import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.epam.test_generator.services.utils.UtilsService.*;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private ProjectTransformer projectTransformer;

    @Autowired
    private UserService userService;

    public List<ProjectDTO> getProjects() {
        return projectTransformer.toDtoList(projectDAO.findAll());
    }

    /**
     * Returns list of {@link Project} to which current user has been assigned.
     * @param authentication {@link Authentication} objects that contains information
     * about current authorized user.
     * @return list of {@link Project} to which current user has been assigned.
     */
    public List<ProjectDTO> getAuthenticatedUserProjects(Authentication authentication) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        return projectTransformer.toDtoList(getProjectsByUserId(userDetails.getId()));
    }

    public List<Project> getProjectsByUserId(Long userId) {
        User user = userService.getUserById(userId);
        checkNotNull(user);
        return projectDAO.findByUsers(user);
    }

    public Project getProjectByProjectId(Long projectId) {
        Project project = projectDAO.findOne(projectId);
        checkNotNull(project);
        return project;
    }

    public Project getProjectByJiraKey(String key) {
        Project project = projectDAO.findByJiraKey(key);
        checkNotNull(project);

        return project;
    }

    public ProjectFullDTO getAuthUserFullProject(Long projectId, Authentication authentication) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getEmail());
        Project project = getProjectByProjectId(projectId);

        userBelongsToProject(project, user);
        return projectTransformer.toFullDto(project);
    }

    /**
     * Creates project specified in projectDTO for current authorized user
     * @param projectDTO project info
     * @param authentication current authorized user
     * @return projectDTO
     */
    public ProjectDTO createProject(ProjectCreateDTO projectDTO, Authentication authentication) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        User authUser = userService.getUserByEmail(userDetails.getEmail());

        Project project = projectTransformer.fromDto(projectDTO);
        project.addUser(authUser);
        project.setActive(true);
        project = projectDAO.save(project);

        return projectTransformer.toDto(project);
    }


    /**
     * Updates project by id with info specified in ProjectDTO
     * @param projectId id of project to update
     * @param projectDTO update info
     */
    public void updateProject(Long projectId, ProjectUpdateDTO projectDTO) {
        Project project = projectDAO.findOne(projectId);
        checkNotNull(project);
        checkProjectIsActive(project);

        projectTransformer.updateFromDto(projectDTO, project);
        project.setId(projectId);
        projectDAO.save(project);
    }

    /**
     * Deletes project from database by id
     * @param projectId id of project to delete
     */
    public void removeProject(Long projectId) {
        Project project = projectDAO.findOne(projectId);
        checkNotNull(project);
        projectDAO.delete(projectId);
    }

    /**
     * Adds user to existing project by user id
     * @param projectId id of project where to add user
     * @param userId id of user to add
     */
    public void addUserToProject(long projectId, long userId) {
        Project project = projectDAO.findOne(projectId);
        checkNotNull(project);
        checkProjectIsActive(project);
        User user = userService.getUserById(userId);
        checkNotNull(user);

        project.getUsers().add(user);
        projectDAO.save(project);
    }

    /**
     * Removes user from project by user id
     * @param projectId id of project
     * @param userId id of user to remove
     */
    public void removeUserFromProject(long projectId, long userId) {
        Project project = projectDAO.findOne(projectId);
        checkNotNull(project);
        checkProjectIsActive(project);
        User user = userService.getUserById(userId);
        checkNotNull(user);

        project.getUsers().remove(user);
        projectDAO.save(project);
    }

    /**
     * Sets project status to inactive and saves to database
     * @param projectId id of project to close
     */
    public void closeProject(long projectId) {
        Project project = projectDAO.findOne(projectId);
        checkNotNull(project);
        checkProjectIsActive(project);
        project.setActive(false);
        projectDAO.save(project);
    }
}
