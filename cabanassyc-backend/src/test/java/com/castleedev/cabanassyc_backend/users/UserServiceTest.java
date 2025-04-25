package com.castleedev.cabanassyc_backend.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.UserDTO;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Implementations.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserDAL userDAL;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserModel testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new UserModel(
            1L, 
            "John", 
            "Doe", 
            "john@example.com", 
            "hashedPassword", 
            25.0, 
            true
        );
        
        testUserDTO = new UserDTO(
            1L, 
            "john@example.com", 
            "John", 
            "Doe", 
            "password123", 
            25.0, 
            true
        );
    }

    @Test
    void getAllUsers_WhenNoUsers_ThrowsNotFoundException() {
        when(userDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> userService.getAllUsers())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No users found");
    }

    @Test
    void getUserById_WhenNotExists_ThrowsNotFoundException() {
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User not found");
    }

    @Test
    void addUser_WithValidData_ReturnsSavedUser() {
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userDAL.save(any(UserModel.class))).thenReturn(testUser);

        UserDTO result = userService.addUser(testUserDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(userDAL).save(argThat(user -> 
            user.getPasswordHashed().equals("hashedPassword") &&
            user.isState()
        ));
    }

    @Test
    void updateUser_WithoutPasswordChange_KeepsOriginalPassword() {
        UserDTO updateDTO = new UserDTO(
            1L, 
            "john@example.com", 
            "John", 
            "Doe", 
            null,
            25.0, 
            true
        );
        
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(userDAL.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDTO result = userService.updateUser(updateDTO);

        assertThat(result).isNotNull();
        verify(userDAL).save(argThat(user -> 
            user.getPasswordHashed().equals("hashedPassword")
        ));
    }

    @Test
    void updateUser_WithNonExistingId_ThrowsNotFoundException() {
        UserDTO updateDTO = new UserDTO(
            99L, 
            "john@example.com", 
            "John", 
            "Doe", 
            null, 
            25.0, 
            true
        );
        when(userDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User not found");
    }
}