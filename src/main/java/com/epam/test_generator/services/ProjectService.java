package com.epam.test_generator.services;

import static com.epam.test_generator.services.utils.UtilsService.checkNotNull;

import com.epam.test_generator.config.security.AuthenticatedUser;
import com.epam.test_generator.dao.interfaces.ProjectDAO;
import com.epam.test_generator.dto.ProjectDTO;
import com.epam.test_generator.dto.ProjectFullDTO;
import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.transformers.ProjectFullTransformer;
import com.epam.test_generator.transformers.ProjectTransformer;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private ProjectTransformer projectTransformer;

    @Autowired
    private ProjectFullTransformer projectFullTransformer;

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
        final User user = checkNotNull(userService.getUserById(userId));
        return projectDAO.findByUsers(user);
    }

    public Project getProjectByProjectId(Long projectId) {
        return checkNotNull(projectDAO.findOne(projectId));
    }

    public Project getProjectByJiraKey(String key) {
        return checkNotNull(projectDAO.findByJiraKey(key));
    }

    public ProjectFullDTO getAuthUserFullProject(Long projectId, Authentication authentication) {
        final AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        final User user = userService.getUserByEmail(userDetails.getEmail());
        final Project project = getProjectByProjectId(projectId);
        project.hasUser(user);
        return projectFullTransformer.toDto(project);
    }

    /**
     * Creates project specified in projectDTO for current authorized user
     * @param projectDTO project info
     * @param authentication current authorized user
     * @return projectDTO
     */
    public ProjectDTO createProject(ProjectDTO projectDTO, Authentication authentication) {
        final AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
        final User authUser = userService.getUserByEmail(userDetails.getEmail());

        Project project = projectTransformer.fromDto(projectDTO);
        project.addUser(authUser);
        project.activate();
        project = projectDAO.save(project);

        return projectTransformer.toDto(project);
    }

    public Long createProjectwithoutPrincipal(ProjectDTO projectDTO) {

        Project project = projectTransformer.fromDto(projectDTO);
        project.activate();

        project = projectDAO.save(project);

        return project.getId();
    }

    /**
     * Updates project by id with info specified in ProjectDTO
     * @param projectId id of project to update
     * @param projectDTO update info
     */
    public void updateProject(Long projectId, ProjectDTO projectDTO) {
        final Project project = checkNotNull(projectDAO.findOne(projectId));
        project.checkIsActive();

        projectTransformer.mapDTOToEntity(projectDTO, project);
        project.setId(projectId);
        projectDAO.save(project);
    }

    /**
     * Deletes project from database by id
     * @param projectId id of project to delete
     */
    public void removeProject(Long projectId) {
        projectDAO.delete(checkNotNull(projectDAO.findOne(projectId)));
    }

    /**
     * Adds user to existing project by user id
     * @param projectId id of project where to add user
     * @param userId id of user to add
     */
    public void addUserToProject(long projectId, long userId) {
        final Project project = checkNotNull(projectDAO.findOne(projectId));
        project.checkIsActive();
        final User user = userService.getUserById(userId);

        project.addUser(user);
        projectDAO.save(project);
    }

    /**
     * Removes user from project by user id
     * @param projectId id of project
     * @param userId id of user to remove
     */
    public void removeUserFromProject(long projectId, long userId) {
        final Project project = checkNotNull(projectDAO.findOne(projectId));
        project.checkIsActive();
        final User user = userService.getUserById(userId);

        project.unsubscribeUser(user);
        projectDAO.save(project);
    }

    /**
     * Sets project status to inactive and saves to database
     * @param projectId id of project to close
     */
    public void closeProject(long projectId) {
        final Project project = checkNotNull(projectDAO.findOne(projectId));
        project.checkIsActive();
        project.close();
        projectDAO.save(project);
    }
}
