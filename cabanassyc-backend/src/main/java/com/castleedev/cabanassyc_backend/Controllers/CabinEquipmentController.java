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

import com.castleedev.cabanassyc_backend.DTO.CabinDTO;
import com.castleedev.cabanassyc_backend.DTO.CabinEquipmentDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinEquipmentService;

@RestController
@RequestMapping("/cabinequipments")
public class CabinEquipmentController {
    
    public final ICabinEquipmentService cabinEquipmentService;

    public CabinEquipmentController(ICabinEquipmentService cabinEquipmentService) {
        this.cabinEquipmentService = cabinEquipmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCabinEquipments() {
        try {
            List<CabinEquipmentDTO> cabinEquipments = cabinEquipmentService.getAllCabinEquipments();
            if (cabinEquipments.isEmpty()) {
                ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "No cabin equipments found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<CabinEquipmentDTO>> apiResponse = new ApiResponse<>(true, "Cabin equipments found successfully", cabinEquipments);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCabinEquipmentById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinEquipmentDTO cabinEquipment = cabinEquipmentService.getCabinEquipmentById(id);
            if (cabinEquipment == null) {
                ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Cabin equipment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(true, "Cabin equipment found successfully", cabinEquipment);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCabinEquipment(@RequestBody CabinEquipmentDTO cabinEquipmentDTO) {
        try {
            if (cabinEquipmentDTO.getCabinId() == null || cabinEquipmentDTO.getEquipmentId() == null) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Cabin and equipment are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            
            CabinEquipmentDTO newCabinEquipment = new CabinEquipmentDTO();
            newCabinEquipment.setCabinId(cabinEquipmentDTO.getCabinId());
            newCabinEquipment.setEquipmentId(cabinEquipmentDTO.getEquipmentId());
            newCabinEquipment.setState(true);
            cabinEquipmentService.addCabinEquipment(newCabinEquipment);
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(true, "Cabin equipment added successfully", newCabinEquipment);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCabinEquipment(@PathVariable("id") Long id, @RequestBody CabinEquipmentDTO cabinEquipmentDTO) {
        try {
            if (id <= 0) {
                ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            cabinEquipmentDTO.setId(id);
            CabinEquipmentDTO updatedCabinEquipment = cabinEquipmentService.updateCabinEquipment(cabinEquipmentDTO);
            if (updatedCabinEquipment == null) {
                ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Cabin equipment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(true, "Cabin equipment updated successfully", updatedCabinEquipment);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteCabinEquipment(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinEquipmentDTO cabinEquipment = cabinEquipmentService.getCabinEquipmentById(id);
            if (cabinEquipment == null) {
                ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Cabin equipment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            cabinEquipmentService.deleteCabinEquipment(id);
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(true, "Cabin equipment deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinEquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
}
