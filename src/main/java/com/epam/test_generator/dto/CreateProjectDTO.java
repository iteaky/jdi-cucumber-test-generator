package com.epam.test_generator.dto;

import com.epam.test_generator.entities.Project;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CreateProjectDTO {
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    private boolean active;

    private Set<UserDTO> users;

    public List<SuitDTO> getSuits() {
        return suits;
    }

    public void setSuits(List<SuitDTO> suits) {
        this.suits = suits;
    }

    private List<SuitDTO> suits;

    private String jiraKey;

    public CreateProjectDTO(String name, String description, boolean active, Set<UserDTO> users) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.users = users;
    }

    public CreateProjectDTO() {
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public Set<UserDTO> getUsers() {
        return users;
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
        if (!(o instanceof CreateProjectDTO)) {
            return false;
        }
        CreateProjectDTO that = (CreateProjectDTO) o;
        return active == that.active &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, active);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }
}
