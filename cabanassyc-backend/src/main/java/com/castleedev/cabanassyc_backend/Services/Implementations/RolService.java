package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IRolService;

@Service
public class RolService implements IRolService {
    
    @Autowired
    private IRolDAL rolDAL;

    @Override
    public List<Rol> getAllRoles() {
        try {
            return rolDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all roles", e);
        }
    }

    @Override
    public Rol getRolById(Long id) {
        try {
            return rolDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a role", e);
        }
    }

    @Override
    public Rol addRol(Rol rol) {
        try {
            return rolDAL.save(rol);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a role", e);
        }
    }

    @Override
    public Rol updateRol(Rol rol) {
        try {
            return rolDAL.save(rol);
        } catch (Exception e) {
            throw new RuntimeException("Error updating a role", e);
        }
    }

    @Override
    public void deleteRol(Long id) {
        try {
            rolDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a role", e);
        }
    }

}