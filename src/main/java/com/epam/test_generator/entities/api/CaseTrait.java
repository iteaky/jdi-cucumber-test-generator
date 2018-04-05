package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Step;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;
import java.util.Objects;

public interface CaseTrait {

    List<Step> getSteps();


    default Step hasStep(Step step) {
        if (getSteps() == null || !getSteps().contains(step)) {
            throw new BadRequestException();
        }
        return step;
    }

    default boolean removeStep(Step step) {
        return Objects.requireNonNull(getSteps()).remove(hasStep(step));
    }

}
