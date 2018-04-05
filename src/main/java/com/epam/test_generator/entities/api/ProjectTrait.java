package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;
import java.util.Set;

public interface ProjectTrait {


    Project getProject();


    default void addSuit(Suit suit) {
        getProject().getSuits().add(suit);
    }

    default Suit hasSuit(Suit suit) {
        final List<Suit> suits = getProject().getSuits();
        if (suits == null || !suits.contains(suit)) {
            throw new BadRequestException(
                "Error: project " + getProject().getName() + " does not have suit " + suit
                    .getName());
        }
        return suit;
    }

    default boolean removeSuit(Suit suit){
        return getProject().getSuits().remove(suit);
    }

    default User hasUser(User user) {
        final Set<User> users = getProject().getUsers();
        if (users == null || !users.contains(user)) {
            throw new BadRequestException(
                "Error: user does not access to project " + getProject().getName());
        }
        return user;
    }

    default boolean unsubscribeUser(User user){
        return getProject().getUsers().remove(user);
    }

    default Project close(){
        final Project project = getProject();
        project.setActive(false);
        return project;
    }

    default void activate(){
        getProject().setActive(true);
    }
}
