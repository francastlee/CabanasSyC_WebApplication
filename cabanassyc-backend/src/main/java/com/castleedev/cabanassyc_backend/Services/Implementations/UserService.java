package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.UserDTO;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserService;

@Service
@Transactional
public class UserService implements IUserService {

    private final IUserDAL userDAL;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserDAL userDAL, PasswordEncoder passwordEncoder) {
        this.userDAL = userDAL;
        this.passwordEncoder = passwordEncoder;
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
        UserModel user = convertToEntity(userDTO);
        updatePasswordIfChanged(user, existingUser);
        UserModel updatedUser = userDAL.save(user);

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

    private void updatePasswordIfChanged(UserModel newUser, UserModel existingUser) {
        if (newUser.getPasswordHashed() != null && 
            !passwordEncoder.matches(newUser.getPasswordHashed(), existingUser.getPasswordHashed())) {
            newUser.setPasswordHashed(passwordEncoder.encode(newUser.getPasswordHashed()));
        } else {
            newUser.setPasswordHashed(existingUser.getPasswordHashed());
        }
    }
}