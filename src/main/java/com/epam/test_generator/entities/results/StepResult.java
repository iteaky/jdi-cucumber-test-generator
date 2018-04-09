package com.epam.test_generator.entities.results;

import com.epam.test_generator.entities.results.api.StepResultTrait;
import javax.persistence.Entity;

@Entity
public class StepResult extends AbstractResult implements StepResultTrait {

    private String description;

    public StepResult() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
