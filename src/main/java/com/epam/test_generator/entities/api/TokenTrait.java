package com.epam.test_generator.entities.api;

import com.epam.test_generator.entities.Token;

import java.util.Date;

public interface TokenTrait {

    Token getInstance();

    default boolean isExpired() {
        return new Date().after(getInstance().getExpiryDate());
    }

}
