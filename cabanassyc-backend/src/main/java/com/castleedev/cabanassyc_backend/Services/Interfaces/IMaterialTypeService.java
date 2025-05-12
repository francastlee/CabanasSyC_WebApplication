package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.MaterialTypeDTO;
public interface IMaterialTypeService {
    
    public List<MaterialTypeDTO> getAllMaterialTypes();
    public MaterialTypeDTO getMaterialTypeById(Long id);
    public MaterialTypeDTO addMaterialType(MaterialTypeDTO materialType);
    public MaterialTypeDTO updateMaterialType(MaterialTypeDTO materialType);
    public void deleteMaterialType(Long id);
    
}
