package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.MaterialType;

public interface IMaterialTypeService {
    
    public List<MaterialType> getAllMaterialTypes();
    public MaterialType getMaterialTypeById(Long id);
    public MaterialType addMaterialType(MaterialType materialType);
    public MaterialType updateMaterialType(MaterialType materialType);
    public void deleteMaterialType(Long id);
    
}
