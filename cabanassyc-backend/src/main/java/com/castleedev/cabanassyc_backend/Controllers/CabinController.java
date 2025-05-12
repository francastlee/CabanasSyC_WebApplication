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
import com.castleedev.cabanassyc_backend.DTO.CabinFullDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cabins")
public class CabinController {
    
    private final ICabinService cabinService;

    public CabinController(ICabinService cabinService) {
        this.cabinService = cabinService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CabinDTO>>> getAllCabins() {
        List<CabinDTO> cabins = cabinService.getAllCabins();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabins found successfully", cabins)
        );
    }

    @GetMapping("/details")
    public ResponseEntity<ApiResponse<List<CabinFullDTO>>> getCabinDetails() {
        List<CabinFullDTO> cabins = cabinService.getAllCabinsWithDetails();
        return ResponseEntity.ok(new ApiResponse<>(true, "Cabins found succesfully with details", cabins));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CabinDTO>> getCabinById(@PathVariable("id") Long id) {
        CabinDTO cabin = cabinService.getCabinById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin found successfully", cabin)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CabinDTO>> addCabin(@Valid @RequestBody CabinDTO cabinDTO) {
        CabinDTO createdCabin = cabinService.addCabin(cabinDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Cabin created successfully", createdCabin));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CabinDTO>> updateCabin(@Valid @RequestBody CabinDTO cabinDTO) {
        CabinDTO updatedCabin = cabinService.updateCabin(cabinDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin updated successfully", updatedCabin)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCabin(@PathVariable("id") Long id) {
        cabinService.deleteCabin(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin deleted successfully", null)
        );
    }
}