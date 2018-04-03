package com.epam.test_generator.controllers.user;

import com.epam.test_generator.controllers.user.request.LoginUserDTO;
import com.epam.test_generator.controllers.user.request.RegistrationUserDTO;
import com.epam.test_generator.controllers.user.responce.UserDTO;
import com.epam.test_generator.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDTOsTransformer {

    @Autowired
    private PasswordEncoder encoder;

    public User createEntityFromDTO(RegistrationUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        return user;
    }


   public UserDTO createUserDTOFromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setAttempts(user.getAttempts());
        userDTO.setLocked(user.isLocked());
        userDTO.setRole(user.getRole().getName());
        return userDTO;
   }

}
