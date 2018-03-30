package com.epam.test_generator.services;

import com.epam.test_generator.config.security.AuthenticatedUser;
import com.epam.test_generator.dao.interfaces.ProjectDAO;
import com.epam.test_generator.dto.CreateProjectDTO;
import com.epam.test_generator.dto.GetProjectDTO;
import com.epam.test_generator.dto.GetProjectFullDTO;
import com.epam.test_generator.dto.UpdateProjectDTO;
import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.test_generator.services.utils.UtilsService.*;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserService userService;

    public List<GetProjectDTO> getProjects() {
        return projectDAO.findAll()
                .stream()
                .map(GetProjectDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns list of {@link Project} to which current user has been assigned.
     *
     * @param authentication {@link Authentication} objects that contains information
     *                       about current authorized user.
     * @return list of {@link Project} to which current user has been assigned.
     */
    public List<GetProjectDTO> getAuthenticatedUserProjects(Authentication authentication) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        return getProjectsByUserId(userDetails.getId())
                .stream()
                .map(GetProjectDTO::new)
                .collect(Collectors.toList());
    }

    public List<Project> getProjectsByUserId(Long userId) {
        User user = checkNotNull(userService.getUserById(userId));
        return projectDAO.findByUsers(user);
    }

    public Project getProjectByProjectId(Long projectId) {
        Project project = projectDAO.findOne(projectId);
        checkNotNull(project);
        return project;
    }

    public GetProjectFullDTO getAuthUserFullProject(Long projectId, Authentication authentication) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getEmail());
        Project project = checkNotNull(projectDAO.getOne(projectId));
        return new GetProjectFullDTO(project.returnIfUserHasAccess(user));
    }

    /**
     * Creates project specified in projectDTO for current authorized user
     *
     * @param createProjectDTO     project info
     * @param authentication current authorized user
     * @return projectDTO
     */
    public GetProjectDTO createProject(CreateProjectDTO createProjectDTO, Authentication authentication) {
        AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        User authUser = userService.getUserByEmail(userDetails.getEmail());

        Project project = new Project(createProjectDTO);
        project.addUser(authUser);
        project = projectDAO.save(project);

        return new GetProjectDTO(project);
    }

    /**
     * Updates project by id with info specified in ProjectDTO
     *
     * @param projectId  id of project to update
     * @param projectDTO update info
     */
    public void updateProject(Long projectId, UpdateProjectDTO projectDTO) {
        Project project = checkNotNull(projectDAO.findOne(projectId));
        project.update(projectDTO);
        projectDAO.save(project);
    }

    /**
     * Deletes project from database by id
     *
     * @param projectId id of project to delete
     */
    public void removeProject(Long projectId) {
        checkNotNull(projectDAO.findOne(projectId));     /// ???
        projectDAO.delete(projectId);
    }

    /**
     * Adds user to existing project by user id
     *
     * @param projectId id of project where to add user
     * @param userId    id of user to add
     */
    public void addUserToProject(long projectId, long userId) {
        Project project = checkNotNull(projectDAO.findOne(projectId));
        User user = checkNotNull(userService.getUserById(userId));
        project.addUser(user);
        projectDAO.save(project);
    }

    /**
     * Removes user from project by user id
     *
     * @param projectId id of project
     * @param userId    id of user to remove
     */
    public void removeUserFromProject(long projectId, long userId) {
        Project project = checkNotNull(projectDAO.findOne(projectId));
        project.removeUserById(userId);
        projectDAO.save(project);
    }

    public void closeProject(Long projectId) {
        Project project = checkNotNull(projectDAO.getOne(projectId));
        project.close();
        projectDAO.save(project);
    }
}
