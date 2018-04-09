package com.epam.test_generator.controllers.suit.request;

import com.epam.test_generator.dto.TagDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class SuitCreateDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer priority;

    @Size(max = 255)
    private String description;

    private Set<TagDTO> tags;

    private Date creationDate;

    public SuitCreateDTO() {
        creationDate = Calendar.getInstance().getTime();
    }

    public SuitCreateDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Suit{name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", tags=" + tags +
                '}';
    }
}
