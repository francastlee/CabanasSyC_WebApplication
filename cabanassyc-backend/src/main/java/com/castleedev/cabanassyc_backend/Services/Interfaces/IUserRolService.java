package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.UserRol;

public interface IUserRolService {
    
    public List<UserRol> getAllUserRoles();
    public UserRol getUserRolById(Long id);
    public UserRol addUserRol(UserRol userRol);
    public UserRol updateUserRol(UserRol userRol);
    public void deleteUserRol(Long id);
    
}
