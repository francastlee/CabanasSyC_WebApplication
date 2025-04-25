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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/material-types")
public class MaterialTypeController {
    
    private final IMaterialTypeService materialTypeService;

    public MaterialTypeController(IMaterialTypeService materialTypeService) {
        this.materialTypeService = materialTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MaterialTypeDTO>>> getAllMaterialTypes() {
        List<MaterialTypeDTO> materialTypes = materialTypeService.getAllMaterialTypes();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material types found successfully", materialTypes)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MaterialTypeDTO>> getMaterialTypeById(@PathVariable("id") Long id) {
        MaterialTypeDTO materialType = materialTypeService.getMaterialTypeById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material type found successfully", materialType)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MaterialTypeDTO>> addMaterialType(@Valid @RequestBody MaterialTypeDTO materialTypeDTO) {
        MaterialTypeDTO createdMaterialType = materialTypeService.addMaterialType(materialTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Material type created successfully", createdMaterialType));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<MaterialTypeDTO>> updateMaterialType(@Valid @RequestBody MaterialTypeDTO materialTypeDTO) {
        MaterialTypeDTO updatedMaterialType = materialTypeService.updateMaterialType(materialTypeDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material type updated successfully", updatedMaterialType)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMaterialType(@PathVariable("id") Long id) {
        materialTypeService.deleteMaterialType(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material type deleted successfully", null)
        );
    }
}