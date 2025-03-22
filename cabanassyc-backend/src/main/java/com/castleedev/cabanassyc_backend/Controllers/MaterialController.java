package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castleedev.cabanassyc_backend.DTO.MaterialDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialService;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    
    private final IMaterialService materialService;

    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMaterials() {
        try {
            List<MaterialDTO> materials = materialService.getAllMaterials();
            if (materials.isEmpty()) {
                ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "No materials found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<MaterialDTO>> apiResponse = new ApiResponse<>(true, "Materials found successfully", materials);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            MaterialDTO material = materialService.getMaterialById(id);
            if (material == null) {
                ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Material not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(true, "Material found successfully", material);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> addMaterial(@RequestBody MaterialDTO material) {
        try {
            if (material.getName() == null) {
                ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Material name is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            MaterialDTO newMaterial = new MaterialDTO();
            newMaterial.setName(material.getName());
            newMaterial.setStock(material.getStock());
            newMaterial.setMaterialTypeId(material.getMaterialTypeId());
            newMaterial.setState(true);
            materialService.addMaterial(newMaterial);
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(true, "Material added successfully", newMaterial);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterial(@PathVariable("id") Long id, @RequestBody MaterialDTO material) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            material.setId(id);
            MaterialDTO materialUpdated = materialService.updateMaterial(material);
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(true, "Material updated successfully", materialUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            MaterialDTO material = materialService.getMaterialById(id);
            if (material == null) {
                ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Material not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            materialService.deleteMaterial(id);
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(true, "Material deleted successfully", material);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}