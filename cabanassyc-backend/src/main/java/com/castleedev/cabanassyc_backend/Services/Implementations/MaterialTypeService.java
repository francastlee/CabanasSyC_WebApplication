package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IMaterialTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialTypeDTO;
import com.castleedev.cabanassyc_backend.Models.MaterialType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialTypeService;
import java.util.ArrayList;

@Service
public class MaterialTypeService implements IMaterialTypeService{
    
    @Autowired
    private IMaterialTypeDAL materialTypeDAL;

    MaterialTypeDTO convertir (MaterialType materialType){
        return new MaterialTypeDTO(
            materialType.getId(), 
            materialType.getName(), 
            materialType.isState()
        );
    }

    MaterialType convertir (MaterialTypeDTO materialTypeDTO){
        return new MaterialType(
            materialTypeDTO.getId(), 
            materialTypeDTO.getName(), 
            materialTypeDTO.isState()
        );
    }

    @Override
    public List<MaterialTypeDTO> getAllMaterialTypes() {
        try {
            List<MaterialType> materialTypes = materialTypeDAL.findAllByStateTrue();
            List<MaterialTypeDTO> materialTypesDTO = new ArrayList<>();
            for (MaterialType materialType : materialTypes) {
                materialTypesDTO.add(convertir(materialType));
            }
            return materialTypesDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all materials", e);
        }
    }

    @Override
    public MaterialTypeDTO getMaterialTypeById(Long id) {
        try {
            MaterialType materialType = materialTypeDAL.findByIdAndStateTrue(id);
            return convertir(materialType);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a material", e);
        }
    }

    @Override
    public MaterialTypeDTO addMaterialType(MaterialTypeDTO materialTypeDTO) {
        try {
            MaterialType materialType = convertir(materialTypeDTO);
            return convertir(materialTypeDAL.save(materialType));
        } catch (Exception e) {
            throw new RuntimeException("Error adding a material", e);
        }
    }

    @Override
    public MaterialTypeDTO updateMaterialType(MaterialTypeDTO materialTypeDTO) {
        try {
            MaterialType materialType = convertir(materialTypeDTO);
            return convertir(materialTypeDAL.save(materialType));
        } catch (Exception e) {
            throw new RuntimeException("Error updating a material", e);
        }
    }

    @Override
    public void deleteMaterialType(Long id) {
        try {
            materialTypeDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a material", e);
        }
    }
}
