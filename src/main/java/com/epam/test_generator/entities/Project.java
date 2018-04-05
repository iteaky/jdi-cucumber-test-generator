package com.epam.test_generator.entities;

import com.epam.test_generator.entities.api.ProjectTrait;
import com.epam.test_generator.entities.api.SuitProjectTrait;
import com.epam.test_generator.entities.api.UsersProjectTrait;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * This class represents Project essence. Besides simple fields like id, name and description,
 * objects of {@link Project} contain suits and users fields. List of {@link Suit} objects
 * represents suits that are linked with current {@link Project}. Set of {@link User} objects that
 * are assigned to current project. "Assigned" means that user has rights at least to see contents
 * of current project. More rights are granted with more significant {@link Role} of a user.
 */
@Entity
public class Project implements ProjectTrait, SuitProjectTrait, UsersProjectTrait {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * This field is used for jira communication.
     */
    private String jiraKey;

    private String name;

    private String description;

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

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    @Override
    public List<Suit> getSuits() {
        return suits;
    }

    public void setSuits(List<Suit> suits) {
        this.suits = suits;
    }

    @Override
    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }

        users.add(user);
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getJiraKey() {
        return jiraKey;
    }

    public void setJiraKey(String jiraKey) {
        this.jiraKey = jiraKey;
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
}
