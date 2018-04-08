package com.epam.test_generator.dto;

import com.epam.test_generator.entities.Status;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 *  This class is needed as the return value in updateSuit method.
 *  It contains two field. The first is {@link SuitDTO} and the second is
 *  {@link List<Long>} (in fact id of {@link StepDTO} with FAILED {@link Status} which belong this
 *  suit or case)
 */
public class SuitUpdateDTO {

    @Size(min = 1, max = 255)
    private String name;

    @Min(value = 1)
    @Max(value = 5)
    private Integer priority;

    @Size(max = 255)
    private String description;

    private Set<TagDTO> tags;

    public SuitUpdateDTO() {

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
}
