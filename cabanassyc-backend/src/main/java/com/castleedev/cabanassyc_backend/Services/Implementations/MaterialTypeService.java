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

import com.castleedev.cabanassyc_backend.DAL.IMaterialTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialTypeDTO;
import com.castleedev.cabanassyc_backend.Models.MaterialType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialTypeService;

@Service
@Transactional
public class MaterialTypeService implements IMaterialTypeService {
    
    private final IMaterialTypeDAL materialTypeDAL;

    public MaterialTypeService(IMaterialTypeDAL materialTypeDAL) {
        this.materialTypeDAL = materialTypeDAL;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "materialType", key = "'all'")
    public List<MaterialTypeDTO> getAllMaterialTypes() {
        List<MaterialType> materialTypes = materialTypeDAL.findAllByStateTrue();
        if (materialTypes.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No material types found"
            );
        }
        
        return materialTypes.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "materialType", key = "#id")
    public MaterialTypeDTO getMaterialTypeById(Long id) {
        MaterialType materialType = materialTypeDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Material type not found"
            ));

        return convertToDTO(materialType);
    }

    @Override
    @CacheEvict(value = "materialType", allEntries = true)
    public MaterialTypeDTO addMaterialType(MaterialTypeDTO materialTypeDTO) {
        MaterialType materialType = convertToEntity(materialTypeDTO);
        materialType.setState(true);
        MaterialType savedMaterialType = materialTypeDAL.save(materialType);

        return convertToDTO(savedMaterialType);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "materialType", key = "#materialTypeDTO.id"),
        @CacheEvict(value = "materialType", key = "'all'")
    })
    public MaterialTypeDTO updateMaterialType(MaterialTypeDTO materialTypeDTO) {
        if (materialTypeDAL.findByIdAndStateTrue(materialTypeDTO.getId()).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Material type not found"
            );
        }
        MaterialType materialType = convertToEntity(materialTypeDTO);
        MaterialType updatedMaterialType = materialTypeDAL.save(materialType);

        return convertToDTO(updatedMaterialType);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "materialType", key = "#id"),
        @CacheEvict(value = "materialType", key = "'all'")
    })
    public void deleteMaterialType(Long id) {
        if(materialTypeDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Material type not found"
            );
        }
        int rowsAffected = materialTypeDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete material type"
            );
        }
    }

    private MaterialTypeDTO convertToDTO(MaterialType materialType) {
        if (materialType == null) return null;

        return new MaterialTypeDTO(
            materialType.getId(), 
            materialType.getName(), 
            materialType.isState()
        );
    }

    private MaterialType convertToEntity(MaterialTypeDTO materialTypeDTO) {
        if (materialTypeDTO == null) return null;

        return new MaterialType(
            materialTypeDTO.getId(), 
            materialTypeDTO.getName(), 
            materialTypeDTO.isState()
        );
    }
}