package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Case;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;
import java.util.Objects;

public interface SuitTrait {

    Suit is();

    List<Case> cases();

    default boolean isRemoved() {
        return Objects.isNull(is().getJiraKey());
    }

    default Case hasCase(Case aCase) {
        if (cases() == null || !cases().contains(aCase)) {
            throw new BadRequestException();
        }
        return aCase;
    }
}
