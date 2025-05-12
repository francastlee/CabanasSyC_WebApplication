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

import com.castleedev.cabanassyc_backend.DTO.CabinEquipmentDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinEquipmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cabin-equipments")
public class CabinEquipmentController {
    
    private final ICabinEquipmentService cabinEquipmentService;

    public CabinEquipmentController(ICabinEquipmentService cabinEquipmentService) {
        this.cabinEquipmentService = cabinEquipmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CabinEquipmentDTO>>> getAllCabinEquipments() {
        List<CabinEquipmentDTO> cabinEquipments = cabinEquipmentService.getAllCabinEquipments();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin equipments found successfully", cabinEquipments)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CabinEquipmentDTO>> getCabinEquipmentById(@PathVariable("id") Long id) {
        CabinEquipmentDTO cabinEquipment = cabinEquipmentService.getCabinEquipmentById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin equipment found successfully", cabinEquipment)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CabinEquipmentDTO>> addCabinEquipment(@Valid @RequestBody CabinEquipmentDTO cabinEquipmentDTO) {
        CabinEquipmentDTO createdCabinEquipment = cabinEquipmentService.addCabinEquipment(cabinEquipmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Cabin equipment created successfully", createdCabinEquipment));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CabinEquipmentDTO>> updateCabinEquipment(@Valid @RequestBody CabinEquipmentDTO cabinEquipmentDTO) {
        CabinEquipmentDTO updatedCabinEquipment = cabinEquipmentService.updateCabinEquipment(cabinEquipmentDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin equipment updated successfully", updatedCabinEquipment)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCabinEquipment(@PathVariable("id") Long id) {
        cabinEquipmentService.deleteCabinEquipment(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin equipment deleted successfully", null)
        );
    }
}