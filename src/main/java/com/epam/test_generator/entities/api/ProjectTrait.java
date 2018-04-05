package com.epam.test_generator.entities.api;

public interface ProjectTrait {

    void setActive(boolean active);

    default void close() {
        setActive(false);
    }

    default void activate(){
        setActive(true);
    }
}
