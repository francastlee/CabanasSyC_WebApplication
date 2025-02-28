package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IUserRolDAL;
import com.castleedev.cabanassyc_backend.Models.UserRol;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserRolService;

@Service
public class UserRolService implements IUserRolService {
    
    @Autowired
    private IUserRolDAL userRolDAL;

    @Override
    public List<UserRol> getAllUserRoles() {
        try {
            return userRolDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all user roles", e);
        }
    }

    @Override
    public UserRol getUserRolById(Long id) {
        try {
            return userRolDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting user role by id", e);
        }
    }

    @Override
    public UserRol addUserRol(UserRol userRol) {
        try {
            return userRolDAL.save(userRol);
        } catch (Exception e) {
            throw new RuntimeException("Error adding user role", e);
        }
    }

    @Override
    public UserRol updateUserRol(UserRol userRol) {
        try {
            return userRolDAL.save(userRol);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user role", e);
        }
    }

    @Override
    public void deleteUserRol(Long id) {
        try {
            userRolDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user role", e);
        }
    }

}