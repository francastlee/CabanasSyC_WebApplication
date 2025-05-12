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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/materials")
public class MaterialController {
    
    private final IMaterialService materialService;

    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MaterialDTO>>> getAllMaterials() {
        List<MaterialDTO> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Materials found successfully", materials)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MaterialDTO>> getMaterialById(@PathVariable("id") Long id) {
        MaterialDTO material = materialService.getMaterialById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material found successfully", material)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MaterialDTO>> addMaterial(@Valid @RequestBody MaterialDTO materialDTO) {
        MaterialDTO createdMaterial = materialService.addMaterial(materialDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Material created successfully", createdMaterial));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<MaterialDTO>> updateMaterial(@Valid @RequestBody MaterialDTO materialDTO) {
        MaterialDTO updatedMaterial = materialService.updateMaterial(materialDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material updated successfully", updatedMaterial)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMaterial(@PathVariable("id") Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material deleted successfully", null)
        );
    }
}