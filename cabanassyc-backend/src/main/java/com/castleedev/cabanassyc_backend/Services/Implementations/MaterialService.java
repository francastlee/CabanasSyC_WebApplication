package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IMaterialDAL;
import com.castleedev.cabanassyc_backend.Models.Material;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialService;

@Service
public class MaterialService implements IMaterialService {
    
    @Autowired
    private IMaterialDAL materialDAL;

    @Override
    public List<Material> getAllMaterials() {
        try {
            return materialDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all materials", e);
        }
    }

    @Override
    public Material getMaterialById(Long id) {
        try {
            return materialDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a material", e);
        }
    }

    @Override
    public Material addMaterial(Material material) {
        try {
            return materialDAL.save(material);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a material", e);
        }
    }

    @Override
    public Material updateMaterial(Material material) {
        try {
            return materialDAL.save(material);
        } catch (Exception e) {
            throw new RuntimeException("Error updating a material", e);
        }
    }

    @Override
    public void deleteMaterial(Long id) {
        try {
            materialDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a material", e);
        }
    }
    
}
