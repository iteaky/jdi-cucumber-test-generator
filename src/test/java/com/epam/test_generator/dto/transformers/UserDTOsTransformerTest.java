package com.epam.test_generator.dto.transformers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import com.epam.test_generator.controllers.user.UserDTOsTransformer;
import com.epam.test_generator.controllers.user.request.RegistrationUserDTO;
import com.epam.test_generator.controllers.user.response.UserDTO;
import com.epam.test_generator.entities.Role;
import com.epam.test_generator.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class UserDTOsTransformerTest {

    @Mock
    private PasswordEncoder encoder;

    private User user;

    private static final String EMAIL = "email";
    private static final String PASSWORD = "pass";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final Integer ATTEMPTS = 0;
    private static final String ROLE = "ROLE";
    private static final Boolean LOCKED = false;

    @InjectMocks
    private UserDTOsTransformer userDTOsTransformer;

    @Before
    public void setUp() throws Exception {
        when(encoder.encode(anyString())).thenReturn(PASSWORD);
        user = new User();
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
    }

    @Test
    public void createEntityFromDTO_RegistrationDTO_Success() {
        RegistrationUserDTO registrationUserDTO = new RegistrationUserDTO();
        registrationUserDTO.setName(NAME);
        registrationUserDTO.setSurname(SURNAME);
        registrationUserDTO.setEmail(EMAIL);
        registrationUserDTO.setPassword(PASSWORD);

        User resultUser = userDTOsTransformer.fromDTO(registrationUserDTO);
        Assert.assertEquals(user, resultUser);
    }

    @Test
    public void createUserDTOFromEntity_User_Success() {
        UserDTO expectedUserDTO = new UserDTO();
        expectedUserDTO.setName(NAME);
        expectedUserDTO.setSurname(SURNAME);
        expectedUserDTO.setEmail(EMAIL);
        expectedUserDTO.setRole(ROLE);
        expectedUserDTO.setLocked(LOCKED);
        expectedUserDTO.setAttempts(ATTEMPTS);

        user.setRole(new Role(ROLE));
        user.setAttempts(ATTEMPTS);
        user.setLocked(LOCKED);

        UserDTO resultUserDTO = userDTOsTransformer.toUserDTO(user);
        Assert.assertEquals(expectedUserDTO, resultUserDTO);
    }

}
