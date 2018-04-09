package com.epam.test_generator.entities.api;

import java.util.Objects;

public interface JiraSuitAndCaseTrait {

    String getJiraKey();

    default boolean isFromJira() {
        return Objects.nonNull(getJiraKey());
    }

}
