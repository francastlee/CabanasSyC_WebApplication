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

@RestController
@RequestMapping("/materialrequests")
public class MaterialRequestController {

    private final IMaterialRequestService materialRequestService;

    public MaterialRequestController(IMaterialRequestService materialRequestService) {
        this.materialRequestService = materialRequestService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMaterialRequests() {
        try {
            List<MaterialRequestDTO> materialRequests = materialRequestService.getAllMaterialRequests();
            if (materialRequests.isEmpty()) {
                ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "No material requests found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<MaterialRequestDTO>> apiResponse = new ApiResponse<>(true, "Material requests found successfully", materialRequests);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialRequestById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            MaterialRequestDTO materialRequest = materialRequestService.getMaterialRequestById(id);
            if (materialRequest == null) {
                ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Material request not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(true, "Material request found successfully", materialRequest);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addMaterialRequest(@RequestBody MaterialRequestDTO materialRequest) {
        try {
            MaterialRequestDTO newMaterialRequest = new MaterialRequestDTO();
            newMaterialRequest.setUserId(materialRequest.getUserId());
            newMaterialRequest.setMaterialId(materialRequest.getMaterialId());
            newMaterialRequest.setQuantity(materialRequest.getQuantity());
            newMaterialRequest.setState(true);
            materialRequestService.addMaterialRequest(newMaterialRequest);
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(true, "Material request added successfully", newMaterialRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterialRequest(@PathVariable("id") Long id, @RequestBody MaterialRequestDTO materialRequest) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            materialRequest.setId(id);
            MaterialRequestDTO materialRequestUpdated = materialRequestService.updateMaterialRequest(materialRequest);
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(true, "Material request updated successfully", materialRequestUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteMaterialRequest(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            MaterialRequestDTO materialRequest = materialRequestService.getMaterialRequestById(id);
            if (materialRequest == null) {
                ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Material request not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            materialRequestService.deleteMaterialRequest(id);
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(true, "Material request deleted successfully", materialRequest);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<MaterialRequestDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}