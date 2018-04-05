package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Project;
import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;
import java.util.Set;

public interface ProjectTrait {


    Project is();

    List<Suit> suits();

    Set<User> users();


    default void addSuit(Suit suit) {
        suits().add(suit);
    }

    default Suit hasSuit(Suit suit) {
        if (suits() == null || !suits().contains(suit)) {
            throw new BadRequestException(
                "Error: project " + is().getName() + " does not have suit " + suit
                    .getName());
        }
        return suit;
    }

    default boolean removeSuit(Suit suit){
        return suits().remove(suit);
    }

    default User hasUser(User user) {
        if (users() == null || !users().contains(user)) {
            throw new BadRequestException(
                "Error: user does not access to project " + is().getName());
        }
        return user;
    }

    default boolean unsubscribeUser(User user){
        return users().remove(user);
    }

    default Project close(){
        final Project project = is();
        project.setActive(false);
        return project;
    }

    default void activate(){
        is().setActive(true);
    }
}
