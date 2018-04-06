package com.epam.test_generator.services;

import com.epam.test_generator.dao.interfaces.TokenDAO;
import com.epam.test_generator.entities.Token;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.TokenMalformedException;
import com.epam.test_generator.services.exceptions.TokenMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Transactional
@Service
public class TokenService {

    @Autowired
    private TokenDAO tokenDAO;

    /**
     * Creates token for user for limited time
     * @param user
     * @param minutes time limit
     * @return user's token
     */
    public Token createToken(User user, Integer minutes) {
        Token token = new Token(user, minutes);
        return tokenDAO.save(token);
    }

    /**
     * Checks token for existing and expiring. Throws exceptions if there is problems with token.
     * @param token
     */
    public void checkToken(String token) {
        Token resetToken = tokenDAO.findByToken(token);
        if (resetToken == null) {
            throw new TokenMissingException("Could not find password reset token.");
        } else if (resetToken.isExpired()) {
            tokenDAO.delete(resetToken);
            throw new TokenMalformedException(
                    "Token has expired, please request a new password reset.");
        }
    }
}
