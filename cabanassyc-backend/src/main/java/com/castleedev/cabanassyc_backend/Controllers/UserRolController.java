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

@RestController
@RequestMapping("/userrols")
public class UserRolController {
    
    private final IUserRolService userRolService;

    public UserRolController(IUserRolService userRolService) {
        this.userRolService = userRolService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUserRols() {
        try {
            List<UserRolDTO> userRols = userRolService.getAllUserRoles();
            if (userRols.isEmpty()) {
                ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "No userRols found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<UserRolDTO>> apiResponse = new ApiResponse<>(true, "UserRols found successfully", userRols);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserRolById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            UserRolDTO userRol = userRolService.getUserRolById(id);
            if (userRol == null) {
                ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "UserRol not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(true, "UserRol found successfully", userRol);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addUserRol(@RequestBody UserRolDTO userRol) {
        try {
            UserRolDTO newUserRol = new UserRolDTO();
            newUserRol.setUserId(userRol.getUserId());
            newUserRol.setRolId(userRol.getRolId());
            newUserRol.setState(true);
            userRolService.addUserRol(newUserRol);
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(true, "UserRol created successfully", newUserRol);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Error during the creation process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserRol(@PathVariable("id") Long id, @RequestBody UserRolDTO userRol) {
        try {
            if (id <= 0) {
                ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            userRol.setId(id);
            UserRolDTO userRolUpdated = userRolService.updateUserRol(userRol);
            if (userRolUpdated == null) {
                ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "UserRol not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(true, "UserRol updated successfully", userRol);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteUserRol(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            UserRolDTO userRol = userRolService.getUserRolById(id);
            if (userRol == null) {
                ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "UserRol not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            userRolService.deleteUserRol(id);
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(true, "UserRol deleted successfully", userRol);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserRolDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}