package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Step;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;
import java.util.Objects;

public interface CaseTrait {

    Case getCase();


    default Step hasStep(Step step) {
        final List<Step> cases = getCase().getSteps();
        if (cases == null || !cases.contains(step)) {
            throw new BadRequestException();
        }
        return step;
    }

    default Case removeStep(Step step){
        final Case aCase = getCase();
        Objects.requireNonNull(aCase.getSteps()).remove(step);
        return aCase;
    }

}
