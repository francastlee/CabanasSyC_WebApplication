package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castleedev.cabanassyc_backend.DTO.RolDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IRolService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RolController {
    
    private final IRolService rolService;

    public RolController(IRolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RolDTO>>> getAllRoles() {
        List<RolDTO> roles = rolService.getAllRoles();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Roles found successfully", roles)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RolDTO>> getRolById(@PathVariable("id") Long id) {
        RolDTO rol = rolService.getRolById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Role found successfully", rol)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RolDTO>> addRol(@Valid @RequestBody RolDTO rolDTO) {
        RolDTO createdRol = rolService.addRol(rolDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Role created successfully", createdRol));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<RolDTO>> updateRol(@Valid @RequestBody RolDTO rolDTO) {
        RolDTO updatedRol = rolService.updateRol(rolDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Role updated successfully", updatedRol)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRol(@PathVariable("id") Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.ok(
            new ApiResponse<Void>(true, "Role deleted successfully", null)
        );
    }
}