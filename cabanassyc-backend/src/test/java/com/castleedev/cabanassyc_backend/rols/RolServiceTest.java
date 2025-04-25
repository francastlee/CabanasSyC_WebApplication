package com.castleedev.cabanassyc_backend.rols;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;
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
import com.castleedev.cabanassyc_backend.DTO.RolDTO;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Services.Implementations.RolService;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @Mock
    private IRolDAL rolDAL;

    @InjectMocks
    private RolService rolService;

    private Rol testRol;
    private RolDTO testRolDTO;

    @BeforeEach
    void setUp() {
        testRol = new Rol(1L, "ADMIN", true);
        testRolDTO = new RolDTO(1L, "ADMIN", true);
    }

    @Test
    void getAllRoles_WhenRolesExist_ReturnsRoleList() {
        List<Rol> mockRoles = List.of(testRol);
        when(rolDAL.findAllByStateTrue()).thenReturn(mockRoles);

        List<RolDTO> result = rolService.getAllRoles();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("ADMIN");
        verify(rolDAL).findAllByStateTrue();
    }

    @Test
    void getAllRoles_WhenNoRoles_ThrowsNotFoundException() {
        when(rolDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> rolService.getAllRoles())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No roles found");
    }

    @Test
    void getRolById_WhenExists_ReturnsRolDTO() {
        when(rolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testRol));

        RolDTO result = rolService.getRolById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("ADMIN");
    }

    @Test
    void getRolById_WhenNotExists_ThrowsNotFoundException() {
        when(rolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rolService.getRolById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Role not found");
    }

    @Test
    void addRol_WithValidData_ReturnsSavedRol() {
        when(rolDAL.save(any(Rol.class))).thenReturn(testRol);

        RolDTO result = rolService.addRol(testRolDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(rolDAL).save(argThat(rol -> 
            rol.isState() && rol.getName().equals("ADMIN")
        ));
    }

    @Test
    void updateRol_WithNonExistingId_ThrowsNotFoundException() {
        RolDTO updateDTO = new RolDTO(99L, "UPDATED_ROLE", true);
        when(rolDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rolService.updateRol(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Role not found");
    }

    @Test
    void deleteRol_WithExistingId_DeletesSuccessfully() {
        when(rolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testRol));
        when(rolDAL.softDeleteById(1L)).thenReturn(1);

        rolService.deleteRol(1L);

        verify(rolDAL).softDeleteById(1L);
    }

    @Test
    void deleteRol_WithNonExistingId_ThrowsNotFoundException() {
        when(rolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rolService.deleteRol(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Role not found");
    }

    @Test
    void deleteRol_WhenDeleteFails_ThrowsInternalError() {
        when(rolDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testRol));
        when(rolDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> rolService.deleteRol(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete role");
    }
}