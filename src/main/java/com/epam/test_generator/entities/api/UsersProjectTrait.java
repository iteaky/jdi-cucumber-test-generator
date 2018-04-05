package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.Set;

public interface UsersProjectTrait {

    Set<User> getUsers();

    String getName();


    default User hasUser(User user) {
        if (getUsers() == null || !getUsers().contains(user)) {
            throw new BadRequestException(
                "Error: user does not access to project " + getName());
        }
        return user;
    }

    default boolean unsubscribeUser(User user) {
        return getUsers().remove(hasUser(user));
    }

}
