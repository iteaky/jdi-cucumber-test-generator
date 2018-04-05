package com.epam.test_generator.controllers.Project.request;

import com.epam.test_generator.dto.UserDTO;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProjectCreateDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    private String jiraKey;


    public ProjectCreateDTO() {
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


    public String getJiraKey() {
        return jiraKey;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectCreateDTO that = (ProjectCreateDTO) o;
        return Objects.equals(name, that.name)
            && Objects.equals(description, that.description)
            && Objects.equals(jiraKey, that.jiraKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, jiraKey);
    }
}
