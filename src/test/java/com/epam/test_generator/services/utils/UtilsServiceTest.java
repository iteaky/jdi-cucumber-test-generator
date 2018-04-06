package com.epam.test_generator.services.utils;

import com.epam.test_generator.entities.Token;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.TokenMissingException;
import org.junit.Test;

public class UtilsServiceTest {

    @Test
    public void checkNotNullToken_ValidToken_OK() {
        Token token = new Token(15);
        UtilsService.checkNotNullToken(token);
    }

    @Test(expected = TokenMissingException.class)
    public void checkNotNullToken_NullToken_Exception() {
        Token token = null;
        UtilsService.checkNotNullToken(token);
    }

}