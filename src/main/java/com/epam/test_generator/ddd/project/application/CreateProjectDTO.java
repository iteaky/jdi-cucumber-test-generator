package com.epam.test_generator.ddd.project.application;

import com.epam.test_generator.dto.SuitDTO;
import com.epam.test_generator.dto.UserDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public CreateProjectDTO(
            String name,
            String description,
            boolean active,
            Set<UserDTO> users,
            List<SuitDTO> suits,
            String jiraKey
    ) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.users = users;
        this.suits = suits;
        this.jiraKey = jiraKey;
    }

    public CreateProjectDTO() {
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

    public Set<UserDTO> getUsers() {
        return users;
    }

    public String getJiraKey() {
        return jiraKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateProjectDTO that = (CreateProjectDTO) o;

        if (active != that.active) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        if (suits != null ? !suits.equals(that.suits) : that.suits != null) return false;
        return jiraKey != null ? jiraKey.equals(that.jiraKey) : that.jiraKey == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (suits != null ? suits.hashCode() : 0);
        result = 31 * result + (jiraKey != null ? jiraKey.hashCode() : 0);
        return result;
    }
}
