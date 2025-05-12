package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.UserRolDTO;

public interface IUserRolService {
    
    public List<UserRolDTO> getAllUserRoles();
    public UserRolDTO getUserRolById(Long id);
    public UserRolDTO addUserRol(UserRolDTO userRolDTO);
    public UserRolDTO updateUserRol(UserRolDTO userRolDTO);
    public void deleteUserRol(Long id);
    
}
