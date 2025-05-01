package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castleedev.cabanassyc_backend.DTO.CurrentUserDTO;
import com.castleedev.cabanassyc_backend.DTO.UserDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            if (users.isEmpty()) {
                ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "No users found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<UserDTO>> apiResponse = new ApiResponse<>(true, "Users found successfully", users);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            UserDTO user = userService.getUserById(id);
            if (user == null) {
                ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<UserDTO> apiResponse = new ApiResponse<>(true, "User found successfully", user);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "Error during the get process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/auth")
    public ApiResponse<?> getCurrentUser() {
        CurrentUserDTO user = userService.getCurrentUser();
        return new ApiResponse<>(true, "User fetched successfully", user);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null) {
                ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "Invalid user");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            UserDTO newUser = new UserDTO();
            newUser.setEmail(userDTO.getEmail());
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setPassword(userDTO.getPassword());
            newUser.setHourlyRate(userDTO.getHourlyRate());
            newUser.setState(true);
            if (userService.addUser(newUser) == null) {
                ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "Error creating user");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
            }
            ApiResponse<UserDTO> apiResponse = new ApiResponse<>(true, "User created successfully", newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<UserDTO> apiResponse = new ApiResponse<>(false, "Error during the create process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.updateUser(userDTO);
        return ResponseEntity.ok(new ApiResponse<>(
            true, 
            "User updated successfully",
            user
        ));   
    }
}