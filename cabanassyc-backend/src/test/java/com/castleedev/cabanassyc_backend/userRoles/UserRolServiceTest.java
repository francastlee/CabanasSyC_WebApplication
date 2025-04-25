package com.castleedev.cabanassyc_backend.userRoles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserRolDAL;
import com.castleedev.cabanassyc_backend.DTO.UserRolDTO;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Models.UserRol;
import com.castleedev.cabanassyc_backend.Services.Implementations.UserRolService;

@ExtendWith(MockitoExtension.class)
class UserRolServiceTest {

    @Mock
    private IUserRolDAL userRolDAL;

    @Mock
    private IUserDAL userDAL;

    @Mock
    private IRolDAL rolDAL;

    @InjectMocks
    private UserRolService userRolService;

    private UserModel testUser;
    private Rol testRol;
    private UserRol testUserRol;
    private UserRolDTO testUserRolDTO;

    @BeforeEach
    void setUp() {
        testUser = new UserModel(1L, "john", "doe", "john@example.com", "password", 1000, true);
        testRol = new Rol(1L, "ADMIN", true);
        testUserRol = new UserRol(1L, testUser, testRol, true);
        testUserRolDTO = new UserRolDTO(1L, 1L, 1L, true);
    }

    @Test
    void getAllUserRoles_WhenUserRolesExist_ReturnsUserRoleList() {
        List<UserRol> mockUserRoles = List.of(testUserRol);
        when(userRolDAL.findAllByStateTrue()).thenReturn(mockUserRoles);

        List<UserRolDTO> result = userRolService.getAllUserRoles();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        assertThat(result.get(0).getRolId()).isEqualTo(1L);
        verify(userRolDAL).findAllByStateTrue();
    }

    @Test
    void getAllUserRoles_WhenNoUserRoles_ThrowsNotFoundException() {
        when(userRolDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> userRolService.getAllUserRoles())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No user roles found");
    }

    @Test
    void getUserRolById_WhenExists_ReturnsUserRolDTO() {
        when(userRolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUserRol));

        UserRolDTO result = userRolService.getUserRolById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRolId()).isEqualTo(1L);
    }

    @Test
    void getUserRolById_WhenNotExists_ThrowsNotFoundException() {
        when(userRolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRolService.getUserRolById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User role not found");
    }

    @Test
    void addUserRol_WithValidData_ReturnsSavedUserRol() {
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(rolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testRol));
        when(userRolDAL.save(any(UserRol.class))).thenReturn(testUserRol);

        UserRolDTO result = userRolService.addUserRol(testUserRolDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(userRolDAL).save(any(UserRol.class));
    }

    @Test
    void addUserRol_WithNonExistingUser_ThrowsNotFoundException() {
        UserRolDTO newDTO = new UserRolDTO(null, 99L, 1L, true);
        when(userDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRolService.addUserRol(newDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User not found");
    }

    @Test
    void addUserRol_WithNonExistingRol_ThrowsNotFoundException() {
        UserRolDTO newDTO = new UserRolDTO(null, 1L, 99L, true);
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(rolDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRolService.addUserRol(newDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Role not found");
    }

    @Test
    void updateUserRol_WithValidData_ReturnsUpdatedUserRol() {
        UserRolDTO updateDTO = new UserRolDTO(1L, 1L, 1L, false);
        
        when(userRolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUserRol));
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(rolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testRol));
        when(userRolDAL.save(any(UserRol.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserRolDTO result = userRolService.updateUserRol(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.isState()).isFalse();
        verify(userRolDAL).save(any(UserRol.class));
    }

    @Test
    void updateUserRol_WithNonExistingId_ThrowsNotFoundException() {
        UserRolDTO updateDTO = new UserRolDTO(99L, 1L, 1L, true);
        when(userRolDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRolService.updateUserRol(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User role not found");
    }

    @Test
    void deleteUserRol_WithExistingId_DeletesSuccessfully() {
        when(userRolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUserRol));
        when(userRolDAL.softDeleteById(1L)).thenReturn(1);

        userRolService.deleteUserRol(1L);

        verify(userRolDAL).softDeleteById(1L);
    }

    @Test
    void deleteUserRol_WithNonExistingId_ThrowsNotFoundException() {
        when(userRolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRolService.deleteUserRol(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User role not found");
    }

    @Test
    void deleteUserRol_WhenDeleteFails_ThrowsInternalError() {
        when(userRolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUserRol));
        when(userRolDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> userRolService.deleteUserRol(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Error deleting user role");
    }
}
