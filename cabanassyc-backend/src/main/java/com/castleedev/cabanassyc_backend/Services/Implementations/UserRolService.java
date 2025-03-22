package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserRolDAL;
import com.castleedev.cabanassyc_backend.DTO.UserRolDTO;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Models.User;
import com.castleedev.cabanassyc_backend.Models.UserRol;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserRolService;
import java.util.ArrayList;
@Service
public class UserRolService implements IUserRolService {
    
    @Autowired
    private IUserRolDAL userRolDAL;

    @Autowired
    private IUserDAL userDAL;

    @Autowired
    private IRolDAL rolDAL;

    UserRolDTO convertir (UserRol userRol) {
        return new UserRolDTO(
            userRol.getId(), 
            userRol.getUser().getId(), 
            userRol.getRol().getId(), 
            userRol.isState()
        );
    }

    UserRol convertir (UserRolDTO userRolDTO) {
        User user = userDAL.findByIdAndStateTrue(userRolDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Rol rol = rolDAL.findByIdAndStateTrue(userRolDTO.getRolId());
        if (rol == null) {
            throw new RuntimeException("Rol not found");
        }
        return new UserRol(
            userRolDTO.getId(), 
            user, 
            rol, 
            userRolDTO.isState()
        );
    }
    @Override
    public List<UserRolDTO> getAllUserRoles() {
        try {
            List<UserRol> userRols = userRolDAL.findAllByStateTrue();
            List<UserRolDTO> userRolDTOs = new ArrayList<UserRolDTO>();
            for (UserRol userRol : userRols) {
                userRolDTOs.add(convertir(userRol));
            }
            return userRolDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all user roles", e);
        }
    }

    @Override
    public UserRolDTO getUserRolById(Long id) {
        try {
            UserRol userRol = userRolDAL.findByIdAndStateTrue(id);
            return convertir(userRol);
        } catch (Exception e) {
            throw new RuntimeException("Error getting user role by id", e);
        }
    }

    @Override
    public UserRolDTO addUserRol(UserRolDTO userRolDTO) {
        try {
            UserRol newUserRol = convertir(userRolDTO);
            return convertir(userRolDAL.save(newUserRol));
        } catch (Exception e) {
            throw new RuntimeException("Error adding user role: " + e.getMessage());
        }
    }

    @Override
    public UserRolDTO updateUserRol(UserRolDTO userRolDTO) {
        try {
            UserRol userRol = convertir(userRolDTO);
            return convertir(userRolDAL.save(userRol));
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