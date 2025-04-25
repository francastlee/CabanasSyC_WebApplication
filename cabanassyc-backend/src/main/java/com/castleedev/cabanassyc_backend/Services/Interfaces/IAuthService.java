package com.castleedev.cabanassyc_backend.Services.Interfaces;

import com.castleedev.cabanassyc_backend.DTO.UserDTO;

public interface IAuthService {

    public UserDTO registerUser(UserDTO userDTO);
    public boolean verifyCredentials(String email, String rawPassword);
}
