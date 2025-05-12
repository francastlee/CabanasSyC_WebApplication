package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.RolDTO;

public interface IRolService {
    
    public List<RolDTO> getAllRoles();
    public RolDTO getRolById(Long id);
    public RolDTO addRol(RolDTO rolDTO);
    public RolDTO updateRol(RolDTO rolDTO);
    public void deleteRol(Long id);
    
}
