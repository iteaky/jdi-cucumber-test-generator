package com.epam.test_generator.controllers;

import com.epam.test_generator.dto.EmailDTO;
import com.epam.test_generator.dto.PasswordResetDTO;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.EmailService;
import com.epam.test_generator.dto.RegistrationUserDTO;
import com.epam.test_generator.services.PasswordService;
import com.epam.test_generator.services.TokenService;
import com.epam.test_generator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controls user registration process.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "/user", params = "action=registration",method = RequestMethod.POST)
    public ResponseEntity registerUserAccount(@RequestBody @Valid RegistrationUserDTO userDTO,  HttpServletRequest request) {

        User user = userService.createUser(userDTO);
        emailService.sendRegistrationMessage(user, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/user", params = {"action=confirm-email","token"})
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        userService.confirmUser(token);

        return new ResponseEntity<>("Your account is verified!", HttpStatus.OK);
    }

    @RequestMapping(value = "/user", params = "action=forgot-password", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity passwordForgot(@RequestBody EmailDTO email,
                                         HttpServletRequest request) throws Exception {

        User user = userService.getUserByEmail(email.getEmail());
        userService.checkUserExist(user);
        emailService.sendResetPasswordMessage(user, request);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/user", params = "action=change-password", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity passwordReset(@RequestBody @Valid PasswordResetDTO passwordResetDTO) {
        passwordService.passwordReset(passwordResetDTO);

        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping(value = "/user", params = {"action=confirm-email-reset","token"})
    public ResponseEntity displayResetPasswordPage(@RequestParam String token) {
        tokenService.checkToken(token);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
