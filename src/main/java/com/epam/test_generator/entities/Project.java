package com.epam.test_generator.entities;

import com.epam.test_generator.dto.CreateProjectDTO;
import com.epam.test_generator.dto.UpdateProjectDTO;
import com.epam.test_generator.services.exceptions.BadRequestException;
import com.epam.test_generator.services.exceptions.ProjectClosedException;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents Project essence. Besides simple fields like id, name and description,
 * objects of {@link Project} contain suits and users fields. List of {@link Suit} objects
 * represents suits that are linked with current {@link Project}. Set of {@link User} objects that
 * are assigned to current project. "Assigned" means that user has rights at least to see contents
 * of current project. More rights are granted with more significant {@link Role} of a user.
 */
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * This field is used for jira communication.
     */
    private String jiraKey;

    private String name;

    private String description;

    public void setSuits(List<Suit> suits) {
        this.suits = suits;
    }

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Suit> suits = new ArrayList<>();

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @Column(columnDefinition = "boolean default true", nullable = false)
    private boolean active;

    public Project() {
    }

    public Project(String name, String description,
                   List<Suit> suits, Set<User> users, boolean active) {
        this.name = name;
        this.description = description;
        this.suits = suits;
        this.users = users;
        this.active = active;
    }

    public Project(CreateProjectDTO createProjectDTO) {
        this.name = createProjectDTO.getName();
        this.description = createProjectDTO.getDescription();
        this.users = createProjectDTO.getUsers().stream().map(User::new).collect(Collectors.toSet());
        this.suits = createProjectDTO.getSuits().stream().map(Suit::new).collect(Collectors.toList());
        this.active = isActive();
        this.jiraKey = createProjectDTO.getJiraKey();
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

    public List<Suit> getSuits() {
        return suits;
    }

    public Set<User> getUsers() {
        return users;
    }

    public boolean isActive() {
        return active;
    }

    public String getJiraKey() {
        return jiraKey;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", jiraKey='" + jiraKey + '\'' +
                ", suits=" + suits +
                ", users=" + users +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return active == project.active &&
                Objects.equals(id, project.id) &&
                Objects.equals(name, project.name) &&
                Objects.equals(description, project.description) &&
                Objects.equals(suits, project.suits) &&
                Objects.equals(jiraKey, project.jiraKey) &&
                Objects.equals(users, project.users);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, suits, users, active, jiraKey);
    }

    public void close() {
        throwIfNotActive();
        active = false;
    }

    public void removeUserById(Long userId) {
        throwIfNotActive();
        users.removeIf(u -> u.getId().equals(userId));
    }

    public void addUser(User user) {
        throwIfNotActive();
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(user);
    }

    public void update(UpdateProjectDTO projectDTO) {
        this.active = projectDTO.isActive(); /// ???
        this.jiraKey = projectDTO.getJiraKey(); /// ???
        //this.suits = projectDTO.getSuits();
        this.name = projectDTO.getName();
        this.description = projectDTO.getDescription();
        this.users = projectDTO.getUsers().stream().map(User::new).collect(Collectors.toSet());
    }

    public Project returnIfUserHasAccess(User user) {
        if (!users.contains(user)) {
            throw new BadRequestException("Error: user does not access to project " + name);
        }
        return this;
    }

    private void throwIfNotActive() {
        if (!active) {
            throw new ProjectClosedException("project with id=" + id + " is closed (readonly)", id);
        }
    }
}
