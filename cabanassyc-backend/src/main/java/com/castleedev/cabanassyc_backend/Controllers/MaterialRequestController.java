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

import com.castleedev.cabanassyc_backend.DTO.MaterialRequestDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialRequestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/material-requests")
public class MaterialRequestController {

    private final IMaterialRequestService materialRequestService;

    public MaterialRequestController(IMaterialRequestService materialRequestService) {
        this.materialRequestService = materialRequestService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MaterialRequestDTO>>> getAllMaterialRequests() {
        List<MaterialRequestDTO> materialRequests = materialRequestService.getAllMaterialRequests();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material requests found successfully", materialRequests)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MaterialRequestDTO>> getMaterialRequestById(@PathVariable("id") Long id) {
        MaterialRequestDTO materialRequest = materialRequestService.getMaterialRequestById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material request found successfully", materialRequest)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MaterialRequestDTO>> addMaterialRequest(@Valid @RequestBody MaterialRequestDTO materialRequestDTO) {
        MaterialRequestDTO createdMaterialRequest = materialRequestService.addMaterialRequest(materialRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Material request created successfully", createdMaterialRequest));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<MaterialRequestDTO>> updateMaterialRequest(@Valid @RequestBody MaterialRequestDTO materialRequestDTO) {
        MaterialRequestDTO updatedMaterialRequest = materialRequestService.updateMaterialRequest(materialRequestDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Material request updated successfully", updatedMaterialRequest)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMaterialRequest(@PathVariable("id") Long id) {
        materialRequestService.deleteMaterialRequest(id);
        return ResponseEntity.ok(
            new ApiResponse<Void>(true, "Material request deleted successfully", null)
        );
    }
}