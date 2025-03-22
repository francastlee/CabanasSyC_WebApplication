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

@RestController
@RequestMapping("/rols")
public class RolController {
    
    private final IRolService rolService;

    public RolController(IRolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRols() {
        try {
            List<RolDTO> rols = rolService.getAllRoles();
            if (rols.isEmpty()) {
                ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "No rols found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<RolDTO>> apiResponse = new ApiResponse<>(true, "Rols found successfully", rols);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRolById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            RolDTO rol = rolService.getRolById(id);
            if (rol == null) {
                ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Rol not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(true, "Rol found successfully", rol);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addRol(@RequestBody RolDTO rol) {
        try {
            RolDTO newRol = new RolDTO();
            newRol.setName(rol.getName());
            newRol.setState(true);
            rolService.addRol(newRol);
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(true, "Rol created successfully", newRol);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Error during the create process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRol(@PathVariable("id") Long id, @RequestBody RolDTO rol) {
        try {
            if (id <= 0) {
                ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            rol.setId(id);
            RolDTO updatedRol = rolService.updateRol(rol);
            if (updatedRol == null) {
                ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Rol not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(true, "Rol updated successfully", updatedRol);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            rolService.deleteRol(id);
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(true, "Rol deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<RolDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}