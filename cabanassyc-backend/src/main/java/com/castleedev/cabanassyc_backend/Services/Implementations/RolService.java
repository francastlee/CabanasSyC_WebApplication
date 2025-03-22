package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.DTO.RolDTO;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IRolService;
import java.util.ArrayList;
@Service
public class RolService implements IRolService {
    
    @Autowired
    private IRolDAL rolDAL;

    RolDTO convertir (Rol rol) {
        return new RolDTO(
            rol.getId(), 
            rol.getName(), 
            rol.isState()
        );
    }

    Rol convertir (RolDTO rolDTO) {
        return new Rol(
            rolDTO.getId(), 
            rolDTO.getName(), 
            rolDTO.isState()
        );
    }

    @Override
    public List<RolDTO> getAllRoles() {
        try {
            List<Rol> rols = rolDAL.findAllByStateTrue();
            List<RolDTO> rolsDTO = new ArrayList<RolDTO>();
            for (Rol rol : rols) {
                rolsDTO.add(convertir(rol));
            }
            return rolsDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all roles", e);
        }
    }

    @Override  
    public RolDTO getRolById(Long id) {
        try {
            Rol rol = rolDAL.findByIdAndStateTrue(id);
            return convertir(rol);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a role", e);
        }
    }

    @Override
    public RolDTO addRol(RolDTO rolDTO) {
        try {
            Rol rol = convertir(rolDTO);
            return convertir(rolDAL.save(rol));
        } catch (Exception e) {
            throw new RuntimeException("Error adding a role", e);
        }
    }

    @Override
    public RolDTO updateRol(RolDTO rolDTO) {
        try {
            Rol rol = convertir(rolDTO);
            return convertir(rolDAL.save(rol));
        } catch (Exception e) {
            throw new RuntimeException("Error updating a role: " + e.getMessage());
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