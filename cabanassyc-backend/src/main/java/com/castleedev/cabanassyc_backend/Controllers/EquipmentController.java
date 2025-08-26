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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {
    
    private final IEquipmentService equipmentService;

    public EquipmentController(IEquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EquipmentDTO>>> getAllEquipments() {
        List<EquipmentDTO> equipments = equipmentService.getAllEquipments();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Equipments found successfully", equipments)
        );  
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EquipmentDTO>> getEquipmentById(@PathVariable("id") Long id) {
        EquipmentDTO equipment = equipmentService.getEquipmentById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Equipment found successfully", equipment)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EquipmentDTO>> addEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        EquipmentDTO createdEquipment = equipmentService.addEquipment(equipmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Equipment created successfully", createdEquipment));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<EquipmentDTO>> updateEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        EquipmentDTO updatedEquipment = equipmentService.updateEquipment(equipmentDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Equipment updated successfully", updatedEquipment)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEquipment(@PathVariable("id") Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.ok(
            new ApiResponse<Void>(true, "Equipment deleted successfully", null)
        );
    }
}