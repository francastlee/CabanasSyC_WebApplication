package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.CurrentUserDTO;
import com.castleedev.cabanassyc_backend.DTO.UserDTO;
import com.castleedev.cabanassyc_backend.DTO.UserRolDTO;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserRolService;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserService;

@Service
@Transactional
public class UserService implements IUserService {

    private final IRolDAL rolDAL;
    private final IUserDAL userDAL;
    private final PasswordEncoder passwordEncoder;
    private final IUserRolService userRolService;

    public UserService(IUserDAL userDAL, PasswordEncoder passwordEncoder,
                   IRolDAL rolDAL, IUserRolService userRolService) {
    this.userDAL = userDAL;
    this.passwordEncoder = passwordEncoder;
    this.rolDAL = rolDAL;
    this.userRolService = userRolService;
}

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<UserModel> users = userDAL.findAllByStateTrue();
        if (users.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No users found"
            );
        }
        return users.stream()
            .map(this::convertToDTO)
            .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public CurrentUserDTO getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel user = userDAL.findByEmailAndStateTrue(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = user.getUserRolList().stream()
            .findFirst()
            .map(userRol -> userRol.getRol().getName())
            .orElse("CLIENTE");

        return new CurrentUserDTO(
            user.getId(),
            user.getFirstName() + " " + user.getLastName(),
            user.getEmail(),
            role
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        UserModel user = userDAL.findByEmailAndStateTrue(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        UserModel user = userDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "User not found"
            ));
        return convertToDTO(user);
    }



    @Override
    public UserDTO addUser(UserDTO userDTO) {        
        UserModel user = convertToEntity(userDTO);
        user.setPasswordHashed(passwordEncoder.encode(userDTO.getPassword()));
        user.setState(true);
        user.setHourlyRate(0);
        UserModel savedUser = userDAL.save(user);
        
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        UserModel existingUser = userDAL.findByIdAndStateTrue(userDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));
        
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setHourlyRate(userDTO.getHourlyRate());
        existingUser.setState(userDTO.getState());
        
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPasswordHashed(passwordEncoder.encode(userDTO.getPassword()));
        }
        
        UserModel updatedUser = userDAL.save(existingUser);
        return convertToDTO(updatedUser);
    }
    
    private UserDTO convertToDTO(UserModel user) {
        return new UserDTO(
            user.getId(), 
            user.getFirstName(), 
            user.getLastName(), 
            user.getEmail(),
            null,
            user.getHourlyRate(), 
            user.isState()
        );
    }
    
    private UserModel convertToEntity(UserDTO userDTO) {
        return new UserModel(
            userDTO.getId(), 
            userDTO.getFirstName(), 
            userDTO.getLastName(), 
            userDTO.getEmail(), 
            null,
            userDTO.getHourlyRate(), 
            userDTO.getState()
        );
    }

    @Transactional
    public UserModel upsertGoogleUser(String email, String name, String pictureUrl) {
        UserModel user = userDAL.findByEmailAndStateTrue(email)
            .map(existing -> {
                if (name != null && !name.isEmpty()) {
                    String[] parts = name.split(" ", 2);
                    existing.setFirstName(parts[0]);
                    if (parts.length > 1) existing.setLastName(parts[1]);
                }
                return userDAL.save(existing);
            })
            .orElseGet(() -> {
                UserModel u = new UserModel();
                if (name != null && !name.isEmpty()) {
                    String[] parts = name.split(" ", 2);
                    u.setFirstName(parts[0]);
                    if (parts.length > 1) u.setLastName(parts[1]);
                }
                u.setEmail(email);
                u.setPasswordHashed("");
                u.setState(true);
                return userDAL.save(u);
            });

        boolean hasUserRole = user.getUserRolList() != null && 
                            user.getUserRolList().stream()
                                .anyMatch(ur -> "USER".equalsIgnoreCase(ur.getRol().getName()));

        if (!hasUserRole) {
            Rol rolUser = rolDAL.findByNameAndStateTrue("USER")
                    .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

            UserRolDTO userRolDTO = new UserRolDTO();
            userRolDTO.setUserId(user.getId());
            userRolDTO.setRolId(rolUser.getId());
            userRolDTO.setState(true);

            userRolService.addUserRol(userRolDTO);
        }

        return user;
    }
}