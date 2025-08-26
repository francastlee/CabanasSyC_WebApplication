package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.CurrentUserDTO;
import com.castleedev.cabanassyc_backend.DTO.UserDTO;

public interface IUserService {
        
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Long id);
    public UserDTO getUserByEmail(String email);
    public UserDTO addUser(UserDTO userDTO);
    public UserDTO updateUser(UserDTO userDTO);
    public CurrentUserDTO getCurrentUser();
}