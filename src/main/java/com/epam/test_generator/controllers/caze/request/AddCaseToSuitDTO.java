package com.epam.test_generator.controllers.caze.request;

import com.epam.test_generator.dto.TagDTO;
import com.epam.test_generator.entities.Status;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class AddCaseToSuitDTO {

    @NotNull
    @Size(min = 1, max = 250)
    private String name;

    @NotNull
    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer priority;

    private String comment;

    @Size(max = 5)
    @Valid
    private Set<TagDTO> tags;

    public AddCaseToSuitDTO() {
    }

    public AddCaseToSuitDTO(String name, String description, Integer priority, String comment, Set<TagDTO> tags) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.comment = comment;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddCaseToSuitDTO)) {
            return false;
        }

        final AddCaseToSuitDTO addCaseToSuitDTO = (AddCaseToSuitDTO) o;

        return ( (name != null ? name.equals(addCaseToSuitDTO.name) : addCaseToSuitDTO.name == null)
                && (description != null ? description.equals(addCaseToSuitDTO.description)
                : addCaseToSuitDTO.description == null)
                && (priority != null ? priority.equals(addCaseToSuitDTO.priority) : addCaseToSuitDTO.priority == null)
                && (comment != null ? comment.equals(addCaseToSuitDTO.comment) : addCaseToSuitDTO.comment == null))
                && (tags != null ? tags.equals(addCaseToSuitDTO.tags) : addCaseToSuitDTO.tags == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "AddCaseToSuitDTO{ name= %s, description= %s, priority= %s, comment= %s, tags= %s };",
                name, description, priority, comment, tags);
    }
}
