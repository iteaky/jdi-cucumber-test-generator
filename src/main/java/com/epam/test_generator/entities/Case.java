package com.epam.test_generator.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Entity
public class Case implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Step> steps;

    private String creationDate;

    private String updateDate;

    private Integer priority;

    @OneToMany(cascade = {CascadeType.ALL})
    private Set<Tag> tags;

    public Case(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        creationDate = formatter.format(Calendar.getInstance().getTime());
        updateDate = formatter.format(Calendar.getInstance().getTime());
    }

    public Case(Long id, String description, List<Step> steps, Integer priority, Set<Tag> tags) {
        this.id = id;
        this.description = description;
        this.steps = steps;
        this.priority = priority;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Step> getSteps() {
        if (steps == null){
            steps = new ArrayList<>();
        }
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Case{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", steps=" + steps +
                ", creationDate='" + creationDate + '\'' +
                ", priority=" + priority +
                ", tags=" + tags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Case)) return false;

        Case aCase = (Case) o;

        if (id != null ? !id.equals(aCase.id) : aCase.id != null) return false;
        if (description != null ? !description.equals(aCase.description) : aCase.description != null) return false;
        if (steps != null ? !steps.equals(aCase.steps) : aCase.steps != null) return false;
        if (creationDate != null ? !creationDate.equals(aCase.creationDate) : aCase.creationDate != null) return false;
        if (updateDate != null ? !updateDate.equals(aCase.updateDate) : aCase.updateDate != null) return false;
        if (priority != null ? !priority.equals(aCase.priority) : aCase.priority != null) return false;
        return tags != null ? tags.equals(aCase.tags) : aCase.tags == null;
    }
}