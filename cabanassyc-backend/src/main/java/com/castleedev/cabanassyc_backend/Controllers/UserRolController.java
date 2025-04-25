package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castleedev.cabanassyc_backend.DTO.UserRolDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserRolService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user-roles")
public class UserRolController {
    
    private final IUserRolService userRolService;

    public UserRolController(IUserRolService userRolService) {
        this.userRolService = userRolService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserRolDTO>>> getAllUserRoles() {
        List<UserRolDTO> userRoles = userRolService.getAllUserRoles();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "User roles found successfully", userRoles)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserRolDTO>> getUserRoleById(@PathVariable("id") Long id) {
        UserRolDTO userRole = userRolService.getUserRolById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "User role found successfully", userRole)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserRolDTO>> addUserRole(@Valid @RequestBody UserRolDTO userRolDTO) {
        UserRolDTO createdUserRole = userRolService.addUserRol(userRolDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "User role created successfully", createdUserRole));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserRolDTO>> updateUserRole(@Valid @RequestBody UserRolDTO userRolDTO) {
        UserRolDTO updatedUserRole = userRolService.updateUserRol(userRolDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "User role updated successfully", updatedUserRole)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserRole(@PathVariable("id") Long id) {
        userRolService.deleteUserRol(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "User role deleted successfully", null)
        );
    }
}