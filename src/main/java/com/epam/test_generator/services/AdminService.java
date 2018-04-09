package com.epam.test_generator.services;

import com.epam.test_generator.controllers.admin.request.UpdateUserRoleDTO;
import com.epam.test_generator.entities.Role;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.BadRoleException;
import com.epam.test_generator.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AdminService {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Changes user role to role specified in updateUserRoleDTO.
     * Searching user occurs by e-mail address.
     * @param updateUserRoleDTO
     */
    public void changeUserRole(UpdateUserRoleDTO updateUserRoleDTO) {

        final User userByEmail = userService.getUserByEmail(updateUserRoleDTO.getEmail());

        if (userByEmail == null) {
            throw new UnauthorizedException(
                    "User with email: " + updateUserRoleDTO.getEmail() + " not found.");
        }
        final Role aNewRole = getRole(updateUserRoleDTO.getRole());

        userByEmail.setRole(aNewRole);
    }

    private Role getRole(String roleName) {

        final Role aRole = roleService.getRoleByName(roleName);

        if (aRole == null) {
            throw new BadRoleException("Invalid name for Role");
        }
        return aRole;
    }


}
