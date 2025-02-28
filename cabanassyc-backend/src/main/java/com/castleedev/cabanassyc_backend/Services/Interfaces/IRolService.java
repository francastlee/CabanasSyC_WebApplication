package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.Rol;

public interface IRolService {
    
    public List<Rol> getAllRoles();
    public Rol getRolById(Long id);
    public Rol addRol(Rol rol);
    public Rol updateRol(Rol rol);
    public void deleteRol(Long id);
    
}
