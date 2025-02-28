package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.Material;

public interface IMaterialService {
    
    public List<Material> getAllMaterials();
    public Material getMaterialById(Long id);
    public Material addMaterial(Material material);
    public Material updateMaterial(Material material);
    public void deleteMaterial(Long id);
    
}
