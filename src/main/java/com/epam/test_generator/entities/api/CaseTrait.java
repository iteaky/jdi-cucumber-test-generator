package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Step;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;
import java.util.Objects;

public interface CaseTrait {

    Case is();

    List<Step> steps();


    default Step hasStep(Step step) {
        if (steps() == null || !steps().contains(step)) {
            throw new BadRequestException();
        }
        return step;
    }

    default Case removeStep(Step step){
        Objects.requireNonNull(steps()).remove(step);
        return is();
    }

}
