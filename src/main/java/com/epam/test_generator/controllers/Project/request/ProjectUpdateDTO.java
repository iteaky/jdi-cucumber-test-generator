package com.epam.test_generator.controllers.Project.request;

import com.epam.test_generator.dto.SuitDTO;
import com.epam.test_generator.dto.UserDTO;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.Size;

public class ProjectUpdateDTO {

    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    private List<SuitDTO> suits;

    private Set<UserDTO> users;

    @Size(max = 255)
    private String jiraKey;

    private Boolean active;

    public ProjectUpdateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SuitDTO> getSuits() {
        return suits;
    }

    public void setSuits(List<SuitDTO> suits) {
        this.suits = suits;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectUpdateDTO that = (ProjectUpdateDTO) o;
        return active == that.active &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(suits, that.suits) &&
            Objects.equals(users, that.users) &&
            Objects.equals(jiraKey, that.jiraKey);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, suits, users, jiraKey, active);
    }
}
