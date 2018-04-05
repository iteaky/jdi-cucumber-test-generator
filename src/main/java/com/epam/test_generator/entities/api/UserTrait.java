package com.epam.test_generator.entities.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.epam.test_generator.entities.Role;
import com.epam.test_generator.entities.User;

public interface UserTrait {

    User getUser();

    default void updatePassword(String password) {
        getUser().setPassword(password);
        invalidateAttempts();
    }

    default void invalidateAttempts() {
        getUser().setLocked(false);
        getUser().setAttempts(0);
    }

    default void updateFailureAttempts(int maxAttempts){
        int attempts = getUser().getAttempts();
        if (maxAttempts <= ++attempts) {
            getUser().setLocked(true);
        }
        getUser().setAttempts(attempts);
    }

    default JWTCreator.Builder getUserBuilder(String elementForUniqueToken) {
        return JWT.create()
                .withIssuer(elementForUniqueToken)
                .withClaim("id", getUser().getId())
                .withClaim("email", getUser().getEmail())
                .withClaim("given_name", getUser().getName())
                .withClaim("family_name",getUser(). getSurname())
                .withClaim("role", getUser().getRole().getName());
    }
}
