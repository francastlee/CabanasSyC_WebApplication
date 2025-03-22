package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IMaterialDAL;
import com.castleedev.cabanassyc_backend.DAL.IMaterialTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialDTO;
import com.castleedev.cabanassyc_backend.Models.Material;
import com.castleedev.cabanassyc_backend.Models.MaterialType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialService;
import java.util.ArrayList;

@Service
public class MaterialService implements IMaterialService {
    
    @Autowired
    private IMaterialDAL materialDAL;

    @Autowired
    private IMaterialTypeDAL materialTypeDAL;

    MaterialDTO convertir (Material material) {
        return new MaterialDTO(
            material.getId(), 
            material.getName(),
            material.getStock(), 
            material.getMaterialType().getId(),
            material.isState()
        );
    }

    Material convertir (MaterialDTO materialDTO) {
        MaterialType materialType = materialTypeDAL.findByIdAndStateTrue(materialDTO.getMaterialTypeId());
        if (materialType == null) {
            throw new RuntimeException("Material type not found");
        }
        return new Material(
            materialDTO.getId(), 
            materialDTO.getName(),
            materialDTO.getStock(), 
            materialType,
            materialDTO.isState()
        );
    }

    
    @Override
    public List<MaterialDTO> getAllMaterials() {
        try {
            List<Material> materials = materialDAL.findAllByStateTrue();
            List<MaterialDTO> materialsDTO = new ArrayList<MaterialDTO>();
            for (Material material : materials) {
                materialsDTO.add(convertir(material));
            }
            return materialsDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all materials" + e.getMessage());
        }
    }

    @Override
    public MaterialDTO getMaterialById(Long id) {
        try {
            Material material = materialDAL.findByIdAndStateTrue(id);
            return convertir(material);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a material", e);
        }
    }

    @Override
    public MaterialDTO addMaterial(MaterialDTO materialDTO) {
        try {
            
            Material material = convertir(materialDTO);
            return convertir(materialDAL.save(material));
        } catch (Exception e) {
            throw new RuntimeException("Error adding a material", e);
        }
    }

    @Override
    public MaterialDTO updateMaterial(MaterialDTO materialDTO) {
        try {
            Material material = convertir(materialDTO);
            return convertir(materialDAL.save(material));
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
