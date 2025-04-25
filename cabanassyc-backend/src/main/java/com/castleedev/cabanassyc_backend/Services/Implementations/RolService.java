package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IRolDAL;
import com.castleedev.cabanassyc_backend.DTO.RolDTO;
import com.castleedev.cabanassyc_backend.Models.Rol;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IRolService;
@Service
@Transactional
public class RolService implements IRolService {
    
    private final IRolDAL rolDAL;

    public RolService(IRolDAL rolDAL) {
        this.rolDAL = rolDAL;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "roles", key = "'all'") 
    public List<RolDTO> getAllRoles() {
        List<Rol> roles = rolDAL.findAllByStateTrue();
        if (roles.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No roles found"
            );
        }
        
        return roles.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "roles", key = "#id")
    public RolDTO getRolById(Long id) {
        Rol rol = rolDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Role not found"
            ));

        return convertToDTO(rol);
    }

    @Override
    @CacheEvict(value = "roles", allEntries = true)
    public RolDTO addRol(RolDTO rolDTO) {
        Rol rol = convertToEntity(rolDTO);
        rol.setState(true);
        Rol savedRol = rolDAL.save(rol);

        return convertToDTO(savedRol);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "roles", key = "#rolDTO.id"), 
        @CacheEvict(value = "roles", key = "'all'")
    })
    public RolDTO updateRol(RolDTO rolDTO) {
        Rol existingRol = rolDAL.findByIdAndStateTrue(rolDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Role not found"
            ));
        existingRol.setName(rolDTO.getName());
        existingRol.setState(rolDTO.isState());

        return convertToDTO(existingRol);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "roles", key = "#id"), 
        @CacheEvict(value = "roles", key = "'all'")
    })
    public void deleteRol(Long id) {
        if (rolDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Role not found"
            );
        }
        int rowsAffected = rolDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete role"
            );
        }
    }

    private RolDTO convertToDTO(Rol rol) {
        if (rol == null) return null;   

        return new RolDTO(
            rol.getId(), 
            rol.getName(), 
            rol.isState()
        );
    }

    private Rol convertToEntity(RolDTO rolDTO) {
        if (rolDTO == null) return null;

        return new Rol(
            rolDTO.getId(), 
            rolDTO.getName(), 
            rolDTO.isState()
        );
    }
}