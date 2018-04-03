package com.epam.test_generator.ddd.project.application;

import com.epam.test_generator.ddd.project.domain.Project;
import com.epam.test_generator.dto.UserDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GetProjectDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    private boolean active;

    private Set<UserDTO> users;

    private String jiraKey;

    public GetProjectDTO() {
    }

    public GetProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.active = project.isActive();
        this.users = project.getUsers().stream().map(UserDTO::new).collect(Collectors.toSet());
        this.jiraKey = project.getJiraKey();
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetProjectDTO)) {
            return false;
        }
        GetProjectDTO that = (GetProjectDTO) o;
        return active == that.active &&
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, active);
    }
}
