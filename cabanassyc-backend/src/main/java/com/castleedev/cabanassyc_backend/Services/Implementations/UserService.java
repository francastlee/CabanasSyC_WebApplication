package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.UserDTO;
import com.castleedev.cabanassyc_backend.Models.User;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserService;
import java.util.ArrayList;

@Service
public class UserService implements IUserService {
    
    @Autowired
    private IUserDAL userDAL;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    UserDTO convertir (User user) {
        return new UserDTO(
            user.getId(), 
            user.getFirstName(), 
            user.getLastName(), 
            user.getEmail(),
            user.getHourlyRate(), 
            user.isState()
        );
    }

    User convertir (UserDTO userDTO) {
        return new User(
            userDTO.getId(), 
            userDTO.getFirstName(), 
            userDTO.getLastName(), 
            userDTO.getEmail(), 
            userDTO.getPassword(),
            userDTO.getHourlyRate(), 
            userDTO.isState()
        );
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userDAL.findAllByStateTrue();
            List<UserDTO> usersDTO = new ArrayList<UserDTO>();
            for (User user : users) {
                usersDTO.add(convertir(user));
            }
            return usersDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all users: " + e.getMessage());
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        try {
            User user = userDAL.findByIdAndStateTrue(id);
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            return convertir(user);
        } catch (Exception e) {
            throw new RuntimeException("Error getting user by id" + e.getMessage());
        }
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        try {
            User user = convertir(userDTO);

            if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
                String passwordHashed = passwordEncoder.encode(userDTO.getPassword());
                user.setPasswordHashed(passwordHashed);
            } else {
                throw new IllegalArgumentException("Password cannot be null or empty");
            }

            return convertir(userDAL.save(user));
        } catch (Exception e) {
            throw new RuntimeException("Error adding user: " + e.getMessage());
        }
    }

    @Override
public UserDTO updateUser(UserDTO userDTO) {
    try {
        User existingUser = userDAL.findByIdAndStateTrue(userDTO.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        User user = convertir(userDTO);
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank() && 
            !passwordEncoder.matches(userDTO.getPassword(), existingUser.getPasswordHashed())) {
            
            String passwordHashed = passwordEncoder.encode(userDTO.getPassword());
            user.setPasswordHashed(passwordHashed);
        } else {
            user.setPasswordHashed(existingUser.getPasswordHashed());
        }
        return convertir(userDAL.save(user));

    } catch (Exception e) {
        throw new RuntimeException("Error updating user", e);
    }
}


    @Override
    public void deleteUser(Long id) {
        try {
            userDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
    
}