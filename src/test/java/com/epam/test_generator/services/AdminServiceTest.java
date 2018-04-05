package com.epam.test_generator.services;

import com.epam.test_generator.controllers.Admin.request.UpdateUserRoleDTO;
import com.epam.test_generator.entities.Role;
import com.epam.test_generator.entities.User;
import com.epam.test_generator.services.exceptions.BadRoleException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {


    private final static String USER_EMAIL = "test@test.com";

    private final static String OLD_USER_ROLE = "GUEST";
    private final static String NEW_USER_ROLE = "ADMIN";

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private AdminService adminService;

    private UpdateUserRoleDTO userRoleDTO;

    private User user;

    private Role oldRole;

    private Role newRole;

    @Before
    public void setUp() {
        userRoleDTO = getUserDtoFor(USER_EMAIL, NEW_USER_ROLE);
        oldRole = getRoleFor(OLD_USER_ROLE);
        newRole = getRoleFor(NEW_USER_ROLE);
        user = getUserFor(USER_EMAIL, oldRole);
    }


    @Test
    public void change_UserRole_Success() {
        assertThat(user.getRole(), is(equalTo(oldRole)));

        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(roleService.getRoleByName(anyString())).thenReturn(newRole);
        adminService.changeUserRole(userRoleDTO);

        assertThat(user.getRole(), is(equalTo(newRole)));
    }


    @Test(expected = BadRoleException.class)
    public void change_UserRole_Exception() {
        assertThat(user.getRole(), is(equalTo(oldRole)));

        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(roleService.getRoleByName(anyString())).thenReturn(null);
        adminService.changeUserRole(userRoleDTO);

    }


    private Role getRoleFor(String userRole) {
        final Role role = new Role();
        role.setName(userRole);
        return role;
    }

    private User getUserFor(String userEmail, Role userRole) {
        final User user = new User();
        user.setEmail(userEmail);
        user.setRole(userRole);
        return user;
    }

    private UpdateUserRoleDTO getUserDtoFor(String userEmail, String userRole) {
        final UpdateUserRoleDTO userRoleDTO = new UpdateUserRoleDTO();
        userRoleDTO.setEmail(userEmail);
        userRoleDTO.setRole(userRole);
        return userRoleDTO;
    }
}