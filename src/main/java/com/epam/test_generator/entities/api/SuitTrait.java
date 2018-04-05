package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Case;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;
import java.util.Objects;

public interface SuitTrait {


    List<Case> getCases();


    default Case hasCase(Case aCase) {
        if (getCases() == null || !getCases().contains(aCase)) {
            throw new BadRequestException();
        }
        return aCase;
    }

    default boolean removeCase(Case aCase){
        return getCases().remove(hasCase(aCase));
    }
}
