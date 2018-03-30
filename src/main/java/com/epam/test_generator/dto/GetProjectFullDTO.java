package com.epam.test_generator.dto;

import com.epam.test_generator.controllers.ProjectController;
import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.Suit;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class, that fully represents {@link Project} entity (all it's fields). This DTO is used only for
 * retrieving Projects from API (see GET method in {@link ProjectController}), because there is no need
 * in container-fields (such as list of {@link Suit} with inner list of {@link Case} etc) for other
 * methods (PUT, POST, DELETE) - in this cases usage of {@link CreateProjectDTO} is more convenient for
 * API's users.
 */
public class GetProjectFullDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @Valid
    private List<SuitDTO> suits;

    @Valid
    private Set<UserDTO> users;

    private boolean active;

    public GetProjectFullDTO() {
    }

    public GetProjectFullDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.suits = project.getSuits().stream().map(SuitDTO::new).collect(Collectors.toList());
        this.users = project.getUsers().stream().map(UserDTO::new).collect(Collectors.toSet());
        this.active = project.isActive();
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

    public List<SuitDTO> getSuits() {
        return suits;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GetProjectFullDTO that = (GetProjectFullDTO) o;

        if (active != that.active) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        if (description != null ? !description.equals(that.description)
                : that.description != null) {
            return false;
        }
        if (suits != null ? !suits.equals(that.suits) : that.suits != null) {
            return false;
        }
        return users != null ? users.equals(that.users) : that.users == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (suits != null ? suits.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}
