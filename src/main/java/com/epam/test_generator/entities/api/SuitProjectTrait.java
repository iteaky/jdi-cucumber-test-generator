package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Suit;
import com.epam.test_generator.services.exceptions.BadRequestException;
import java.util.List;

public interface SuitProjectTrait {


    List<Suit> getSuits();

    String getName();

    default void addSuit(Suit suit) {
        getSuits().add(suit);
    }

    default Suit hasSuit(Suit suit) {
        if (getSuits() == null || !getSuits().contains(suit)) {
            throw new BadRequestException(
                "Error: project " + getName() + " does not have suit " + suit
                    .getName());
        }
        return suit;
    }

    default boolean removeSuit(Suit suit){
        return getSuits().remove(hasSuit(suit));
    }

}
