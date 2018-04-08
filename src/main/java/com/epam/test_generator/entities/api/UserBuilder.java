package com.epam.test_generator.entities.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.epam.test_generator.entities.User;

public class UserBuilder {
    public static JWTCreator.Builder getUserBuilder(User user, String elementForUniqueToken) {
        return JWT.create()
                .withIssuer(elementForUniqueToken)
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("given_name", user.getName())
                .withClaim("family_name", user.getSurname())
                .withClaim("role", user.getRole().getName());
    }
}
