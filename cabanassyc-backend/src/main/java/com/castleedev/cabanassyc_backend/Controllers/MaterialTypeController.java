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

import com.castleedev.cabanassyc_backend.DTO.MaterialTypeDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialTypeService;

@RestController
@RequestMapping("/materialtypes")
public class MaterialTypeController {
    
    private final IMaterialTypeService materialTypeService;

    public MaterialTypeController(IMaterialTypeService materialTypeService) {
        this.materialTypeService = materialTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMaterialTypes() {
        try {
            List<MaterialTypeDTO> materialTypes = materialTypeService.getAllMaterialTypes();
            if (materialTypes.isEmpty()) {
                ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "No material types found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<MaterialTypeDTO>> apiResponse = new ApiResponse<>(true, "Material types found successfully", materialTypes);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialTypeById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            MaterialTypeDTO materialType = materialTypeService.getMaterialTypeById(id);
            if (materialType == null) {
                ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Material type not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(true, "Material type found successfully", materialType);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addMaterialType(@RequestBody MaterialTypeDTO materialType) {
        try {
            if (materialType.getName() == null) {
                ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Material type name is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            MaterialTypeDTO newMaterialType = new MaterialTypeDTO();
            newMaterialType.setName(materialType.getName());
            newMaterialType.setState(true);
            materialTypeService.addMaterialType(newMaterialType);
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(true, "Material type added successfully", newMaterialType);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterialType(@PathVariable("id") Long id, @RequestBody MaterialTypeDTO materialType) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            materialType.setId(id);
            MaterialTypeDTO updatedMaterialType = materialTypeService.updateMaterialType(materialType);
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(true, "Material type updated successfully", updatedMaterialType);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteMaterialType(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            if (materialTypeService.getMaterialTypeById(id) == null) {
                ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Material type not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            materialTypeService.deleteMaterialType(id);
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(true, "Material type deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
}
