package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserRolDAL;
import com.castleedev.cabanassyc_backend.DTO.UserRolDTO;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Models.UserRol;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IUserRolService;

@Service
@Transactional
public class UserRolService implements IUserRolService {
    
    private final IUserRolDAL userRolDAL;
    private final IUserDAL userDAL;
    private final IRolDAL rolDAL;

    public UserRolService(
            IUserRolDAL userRolDAL,
            IUserDAL userDAL,
            IRolDAL rolDAL) {
        this.userRolDAL = userRolDAL;
        this.userDAL = userDAL;
        this.rolDAL = rolDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRolDTO> getAllUserRoles() {
        List<UserRol> userRoles = userRolDAL.findAllByStateTrue();
        if (userRoles.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No user roles found"
            );
        }

        return userRoles.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        
    }

    @Override
    @Transactional(readOnly = true)
    public UserRolDTO getUserRolById(Long id) {
        UserRol userRol = userRolDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "User role not found"
            ));

        return convertToDTO(userRol);
    }

    @Override
    public UserRolDTO addUserRol(UserRolDTO userRolDTO) {
        UserRol userRol = convertToEntity(userRolDTO);
        userRol.setState(true);
        UserRol savedUserRol = userRolDAL.save(userRol);

        return convertToDTO(savedUserRol);
    }

    @Override
    public UserRolDTO updateUserRol(UserRolDTO userRolDTO) {
        if (userRolDAL.findByIdAndStateTrue(userRolDTO.getId()).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "User role not found"
            );
        }
        UserRol userRol = convertToEntity(userRolDTO);
        UserRol updatedUserRol = userRolDAL.save(userRol);

        return convertToDTO(updatedUserRol);
    }

    @Override
    public void deleteUserRol(Long id) {
        if (userRolDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "User role not found"
            );
        }
        int rowsAffected = userRolDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error deleting user role"
            );
        }
    }

    private UserRolDTO convertToDTO(UserRol userRol) {
        if (userRol == null) return null;
        
        return new UserRolDTO(
            userRol.getId(), 
            userRol.getUser().getId(), 
            userRol.getRol().getId(), 
            userRol.isState()
        );
    }

    private UserRol convertToEntity(UserRolDTO userRolDTO) {
        if (userRolDTO == null) return null;

        UserModel user = userDAL.findByIdAndStateTrue(userRolDTO.getUserId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));
        Rol rol = rolDAL.findByIdAndStateTrue(userRolDTO.getRolId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role not found"
            ));

        return new UserRol(
            userRolDTO.getId(), 
            user, 
            rol, 
            userRolDTO.isState()
        );
    }
}