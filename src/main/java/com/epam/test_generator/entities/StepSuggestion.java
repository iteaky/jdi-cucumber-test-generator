package com.epam.test_generator.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;


/**
 * This class represents example of possible steps for user.
 */
@Entity
public class StepSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @NotNull
    private Long version;

    private String content;

    @Enumerated(EnumType.STRING)
    private StepType type;

    public StepSuggestion(Long id, String content, StepType type, Long version) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.version = version;
    }

    public StepSuggestion(String content, StepType type, Long version) {
        this.content = content;
        this.type = type;
        this.version = version;
    }

    public StepSuggestion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StepType getType() {
        return type;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StepSuggestion that = (StepSuggestion) o;

        return id.equals(that.id) && content.equals(that.content) && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
