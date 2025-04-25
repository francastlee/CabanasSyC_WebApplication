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

import com.castleedev.cabanassyc_backend.DTO.CabinTypeDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cabin-types")
public class CabinTypeController {
    
    private final ICabinTypeService cabinTypeService;

    public CabinTypeController(ICabinTypeService cabinTypeService) {
        this.cabinTypeService = cabinTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CabinTypeDTO>>> getAllCabinTypes() {
        List<CabinTypeDTO> cabinTypes = cabinTypeService.getAllCabinTypes();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin types found successfully", cabinTypes)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CabinTypeDTO>> getCabinTypeById(@PathVariable("id") Long id) {
        CabinTypeDTO cabinType = cabinTypeService.getCabinTypeById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin type found successfully", cabinType)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CabinTypeDTO>> addCabinType(@Valid @RequestBody CabinTypeDTO cabinTypeDTO) {
        CabinTypeDTO createdCabinType = cabinTypeService.addCabinType(cabinTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Cabin type created successfully", createdCabinType));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CabinTypeDTO>> updateCabinType(@Valid @RequestBody CabinTypeDTO cabinTypeDTO) {
        CabinTypeDTO updatedCabinType = cabinTypeService.updateCabinType(cabinTypeDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin type updated successfully", updatedCabinType)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCabinType(@PathVariable("id") Long id) {
        cabinTypeService.deleteCabinType(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin type deleted successfully", null)
        );
    }
}