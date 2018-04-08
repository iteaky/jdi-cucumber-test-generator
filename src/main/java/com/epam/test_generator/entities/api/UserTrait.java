package com.epam.test_generator.entities.api;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.epam.test_generator.entities.Role;

public interface UserTrait {

    void setPassword(String password);

    void setAttempts(int i);

    Integer getAttempts();

    void setLocked(boolean b);

    default void updatePassword(String password) {
        setPassword(password);
        resetAttempts();
    }

    default void resetAttempts() {
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

    default void lock() {
        setLocked(true);
    }

    default void unLock() {
        setLocked(false);
    }
}
