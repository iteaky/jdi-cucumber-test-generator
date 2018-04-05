package com.epam.test_generator.controllers.Project;

import com.epam.test_generator.controllers.Project.request.ProjectCreateDTO;
import com.epam.test_generator.controllers.Project.request.ProjectUpdateDTO;
import com.epam.test_generator.controllers.Project.response.ProjectDTO;
import com.epam.test_generator.controllers.Project.response.ProjectFullDTO;
import com.epam.test_generator.dto.SuitDTO;
import com.epam.test_generator.dto.UserDTO;
import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.transformers.SuitTransformer;
import com.epam.test_generator.transformers.UserTransformer;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectTransformer {

    // should be deleted after merging!
    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private SuitTransformer suitTransformer;

    public Project fromDto(ProjectCreateDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setJiraKey(projectDTO.getJiraKey());
        return project;
    }

    public ProjectDTO toDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setJiraKey(project.getJiraKey());
        projectDTO.setActive(project.isActive());
        // should be changed on our user
        if (project.getUsers() != null) {
            Set<UserDTO> outputUsers = project.getUsers().stream()
                .map(userTransformer::toDto).collect(Collectors.toSet());
            projectDTO.setUsers(outputUsers);
        }
        return projectDTO;
    }

    public ProjectFullDTO toFullDto(Project project) {
        ProjectFullDTO projectFullDTO = new ProjectFullDTO();
        projectFullDTO.setId(project.getId());
        projectFullDTO.setName(project.getName());
        projectFullDTO.setDescription(project.getDescription());
        projectFullDTO.setJiraKey(project.getJiraKey());

        // should be changed on our userTransformer and suitTransformer
        if (project.getUsers() != null) {
            Set<UserDTO> outputUsers = project.getUsers().stream()
                .map(userTransformer::toDto).collect(Collectors.toSet());
        }
        if (project.getSuits() != null) {
            List<SuitDTO> outputSuits = project.getSuits().stream()
                .map(suitTransformer::toDto).collect(Collectors.toList());
            projectFullDTO.setSuits(outputSuits);
        }
        projectFullDTO.setActive(project.isActive());
        return projectFullDTO;
    }

    public Project updateFromDto(ProjectUpdateDTO projectUpdateDTO, Project project) {
        if (projectUpdateDTO.getName() != null) {
            project.setName(projectUpdateDTO.getName());
        }
        if (projectUpdateDTO.getDescription() != null) {
            project.setDescription(projectUpdateDTO.getDescription());
        }
        if (projectUpdateDTO.getJiraKey() != null) {
            project.setJiraKey(projectUpdateDTO.getJiraKey());
        }
        if (projectUpdateDTO.isActive() != null) {
            project.setActive(projectUpdateDTO.isActive());
        }
        // change on out transformers
        if (projectUpdateDTO.getUsers() != null) {
            Set<User> inputUsers = projectUpdateDTO.getUsers().stream()
                .map(userTransformer::fromDto).collect(Collectors.toSet());
            project.setUsers(inputUsers);
        }
        if (projectUpdateDTO.getSuits() != null) {
            List<Suit> outputSuits = projectUpdateDTO.getSuits().stream()
                .map(suitTransformer::fromDto).collect(Collectors.toList());
            project.setSuits(outputSuits);
        }
        return project;
    }

    public List<ProjectDTO> toDtoList(List<Project> projects) {
        return projects.stream().map(this::toDto).collect(Collectors.toList());
    }

}
