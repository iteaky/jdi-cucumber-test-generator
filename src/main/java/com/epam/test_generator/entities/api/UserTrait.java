package com.epam.test_generator.entities.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.epam.test_generator.entities.Role;
import com.epam.test_generator.entities.User;

public interface UserTrait {

    Long getId();

    String getName();

    String getSurname();

    String getEmail();

    void setPassword(String password);

    Role getRole();

    void setAttempts(int i);

    Integer getAttempts();

    void setLocked(boolean b);

    default void updatePassword(String password) {
        setPassword(password);
        invalidateAttempts();
    }

    default void invalidateAttempts() {
        setLocked(false);
        setAttempts(0);
    }

    default void updateFailureAttempts(int maxAttempts){
        int attempts = getAttempts();
        if (maxAttempts <= ++attempts) {
            setLocked(true);
        }
        setAttempts(attempts);
    }

    default JWTCreator.Builder getUserBuilder(String elementForUniqueToken) {
        return JWT.create()
                .withIssuer(elementForUniqueToken)
                .withClaim("id", getId())
                .withClaim("email", getEmail())
                .withClaim("given_name", getName())
                .withClaim("family_name", getSurname())
                .withClaim("role", getRole().getName());
    }

    default void lock() {
        setLocked(true);
    }

    default void unLock() {
        setLocked(false);
    }
}
