package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IMaterialTypeDAL;
import com.castleedev.cabanassyc_backend.Models.MaterialType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialTypeService;

@Service
public class MaterialTypeService implements IMaterialTypeService{
    
    @Autowired
    private IMaterialTypeDAL materialTypeDAL;

    @Override
    public List<MaterialType> getAllMaterialTypes() {
        try {
            return materialTypeDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all materials", e);
        }
    }

    @Override
    public MaterialType getMaterialTypeById(Long id) {
        try {
            return materialTypeDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a material", e);
        }
    }

    @Override
    public MaterialType addMaterialType(MaterialType materialType) {
        try {
            return materialTypeDAL.save(materialType);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a material", e);
        }
    }

    @Override
    public MaterialType updateMaterialType(MaterialType materialType) {
        try {
            return materialTypeDAL.save(materialType);
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
