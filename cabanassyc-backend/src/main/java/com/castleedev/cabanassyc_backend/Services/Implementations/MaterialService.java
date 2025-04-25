package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IMaterialDAL;
import com.castleedev.cabanassyc_backend.DAL.IMaterialTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialDTO;
import com.castleedev.cabanassyc_backend.Models.Material;
import com.castleedev.cabanassyc_backend.Models.MaterialType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialService;


@Service
@Transactional
public class MaterialService implements IMaterialService {

    private final IMaterialDAL materialDAL;
    private final IMaterialTypeDAL materialTypeDAL;

    public MaterialService(IMaterialDAL materialDAL, IMaterialTypeDAL materialTypeDAL) {
        this.materialDAL = materialDAL;
        this.materialTypeDAL = materialTypeDAL;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "materials", key = "'all'")
    public List<MaterialDTO> getAllMaterials() {
        List<Material> materials = materialDAL.findAllByStateTrue();
        if (materials.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No materials found"
            );  
        }

        return materials.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "materials", key = "#id")
    public MaterialDTO getMaterialById(Long id) {
        Material material = materialDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            ));

        return convertToDTO(material);
    }

    @Override
    @CacheEvict(value = "materials", allEntries = true)
    public MaterialDTO addMaterial(MaterialDTO materialDTO) {
        Material material = convertToEntity(materialDTO);
        material.setState(true);
        Material savedMaterial = materialDAL.save(material);

        return convertToDTO(savedMaterial);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "materials", key = "#materialDTO.id"),
        @CacheEvict(value = "materials", key = "'all'")
    })
    public MaterialDTO updateMaterial(MaterialDTO materialDTO) {
        if (materialDAL.findByIdAndStateTrue(materialDTO.getId()).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Material not found"
            );
        }
        Material material = convertToEntity(materialDTO);
        Material updatedMaterial = materialDAL.save(material);
        
        return convertToDTO(updatedMaterial);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "materials", key = "#id"),
        @CacheEvict(value = "materials", key = "'all'")
    })
    public void deleteMaterial(Long id) {
        if (materialDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Material not found"
            );
        }
        int rowsAffected = materialDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed deleting material"
            );
        }
    }

    private MaterialDTO convertToDTO(Material material) {
        if (material == null) return null;
        
        return new MaterialDTO(
            material.getId(),
            material.getName(),
            material.getStock(),
            material.getMaterialType().getId(),
            material.isState()
        );
    }

    private Material convertToEntity(MaterialDTO materialDTO) {
        if (materialDTO == null) return null;

        MaterialType materialType = materialTypeDAL.findByIdAndStateTrue(materialDTO.getMaterialTypeId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Material type not found"
            )); 

        return new Material(
            materialDTO.getId(),
            materialDTO.getName(),
            materialDTO.getStock(),
            materialType,
            materialDTO.isState()
        );
    }
}