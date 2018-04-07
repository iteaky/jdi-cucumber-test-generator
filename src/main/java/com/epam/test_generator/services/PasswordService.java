package com.epam.test_generator.services;

import com.epam.test_generator.dao.interfaces.TokenDAO;
import com.epam.test_generator.dto.PasswordResetDTO;
import com.epam.test_generator.entities.Token;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.IncorrectURI;
import com.epam.test_generator.services.exceptions.TokenMissingException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:email.messages.properties")
public class PasswordService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenDAO tokenDAO;

    @Value("${override.domain:#{null}}")
    private String OVERRIDE_DOMAIN;

    private final static String PASSWORD_RESET_PATH = "/user/password";
    private final static String PASSWORD_RESET_PARAM = "action=check-temp-token";
    private final static String CONFIRM_ACCOUNT_PATH = "/user";
    private final static String CONFIRM_ACCOUNT_PARAM = "action=confirm-email";
    private final static String TOKEN = "&token=";

    /**
     * Generates URI path to reset password by user token
     *
     * @param token user token
     * @return URI path
     */
    public String createResetUrl(HttpServletRequest request, Token token) {
        return getSecurityUrl(request, PASSWORD_RESET_PATH, PASSWORD_RESET_PARAM, token).toString();
    }

    /**
     * Generates URI path to confirm user account by token
     *
     * @param token user token
     * @return URI path
     */
    public String createConfirmUrl(HttpServletRequest request, Token token) {
        return getSecurityUrl(request, CONFIRM_ACCOUNT_PATH, CONFIRM_ACCOUNT_PARAM, token)
            .toString();
    }


    /**
     * Resets password for user specified in passwordResetDTO
     *
     * @param passwordResetDTO info about user token and password
     */
    public void passwordReset(PasswordResetDTO passwordResetDTO) {
        String token = passwordResetDTO.getToken();
        Token resetToken = tokenDAO.findByToken(token);
        if (resetToken == null) {
            throw new TokenMissingException("Token is invalid");
        }
        User user = resetToken.getUser();
        String updatedPassword = passwordEncoder.encode(passwordResetDTO.getPassword());
        userService.updatePassword(updatedPassword, user.getEmail());
        tokenDAO.delete(resetToken);
    }

    public Token getTokenByName(String token) {
        return tokenDAO.findByToken(token);
    }

    private URI getSecurityUrl(HttpServletRequest request, String path, String param, Token token) {
        try {
            if (OVERRIDE_DOMAIN == null) {
                return new URI(request.getScheme(),
                    null,
                    request.getServerName(),
                    request.getServerPort(),
                    request.getContextPath() + path,
                    param + TOKEN + token.getToken(),
                    null);
            }
            return new URI(OVERRIDE_DOMAIN + path + "?" + param + TOKEN + token.getToken());
        } catch (URISyntaxException e) {
            throw new IncorrectURI(e.getMessage());
        }
    }
}
