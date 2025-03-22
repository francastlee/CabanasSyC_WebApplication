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

import com.castleedev.cabanassyc_backend.DTO.EquipmentDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IEquipmentService;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {
    
    private final IEquipmentService equipmentService;

    public EquipmentController(IEquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllEquipments() {
        try {
            List<EquipmentDTO> equipments = equipmentService.getAllEquipments();
            if (equipments.isEmpty()) {
                ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "No equipments found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<EquipmentDTO>> apiResponse = new ApiResponse<>(true, "Equipments found successfully", equipments);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            EquipmentDTO equipment = equipmentService.getEquipmentById(id);
            if (equipment == null) {
                ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Equipment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(true, "Equipment found successfully", equipment);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the get process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addEquipment(@RequestBody EquipmentDTO equipment) {
        try {
            if (equipment.getName() == null) {
                ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Name must be a valid name");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            EquipmentDTO newEquipment = new EquipmentDTO();
            newEquipment.setName(equipment.getName());
            newEquipment.setState(true);
            equipmentService.addEquipment(newEquipment);
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(true, "Equipment added successfully", newEquipment);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable("id") Long id, @RequestBody EquipmentDTO equipment) {
        try {
            if (id <= 0) {
                ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            equipment.setId(id);
            EquipmentDTO updatedEquipment = equipmentService.updateEquipment(equipment);
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(true, "Equipment updated successfully", updatedEquipment);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            EquipmentDTO equipment = equipmentService.getEquipmentById(id);
            if (equipment == null) {
                ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Equipment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            equipmentService.deleteEquipment(id);
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(true, "Equipment deleted successfully", equipment);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<EquipmentDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
}
