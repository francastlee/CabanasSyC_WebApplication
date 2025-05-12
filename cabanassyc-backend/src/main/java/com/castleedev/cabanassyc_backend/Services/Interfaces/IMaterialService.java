package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.MaterialDTO;

public interface IMaterialService {
    
    public List<MaterialDTO> getAllMaterials();
    public MaterialDTO getMaterialById(Long id);
    public MaterialDTO addMaterial(MaterialDTO material);
    public MaterialDTO updateMaterial(MaterialDTO material);
    public void deleteMaterial(Long id);
    
}
