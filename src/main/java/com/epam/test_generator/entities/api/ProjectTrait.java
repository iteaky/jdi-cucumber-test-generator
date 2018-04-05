package com.epam.test_generator.entities.api;

import com.epam.test_generator.services.exceptions.ProjectClosedException;

public interface ProjectTrait {

    void setActive(boolean active);

    boolean isActive();

    Long getId();

    default void checkIsActive() {
        if (!isActive()) {
            throw new ProjectClosedException("project with id=" + getId() +
                " is closed (readonly)", getId());
        }
    }

    default void close() {
        setActive(false);
    }

    default void activate(){
        setActive(true);
    }
}
