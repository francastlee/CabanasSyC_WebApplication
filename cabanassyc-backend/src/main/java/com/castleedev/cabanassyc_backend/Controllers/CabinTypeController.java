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

@RestController
@RequestMapping("/cabintypes")
public class CabinTypeController {
    
    private final ICabinTypeService cabinTypeService;

    public CabinTypeController(ICabinTypeService cabinTypeService) {
        this.cabinTypeService = cabinTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCabinTypes() {
        try {
            List<CabinTypeDTO> cabinTypes = cabinTypeService.getAllCabinTypes();
            if (cabinTypes.isEmpty()) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "No cabin types found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<CabinTypeDTO>> apiResponse = new ApiResponse<>(true, "Cabin types found successfully", cabinTypes);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCabinTypeById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinTypeDTO cabinType = cabinTypeService.getCabinTypeById(id);
            if (cabinType == null) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Cabin type not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(true, "Cabin type found successfully", cabinType);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCabinType(@RequestBody CabinTypeDTO cabinType) {
        try {
            if (cabinType == null) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Cabin type must not be null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinTypeDTO newCabinType = new CabinTypeDTO();
            newCabinType.setName(cabinType.getName());
            newCabinType.setCapacity(cabinType.getCapacity());
            newCabinType.setPrice(cabinType.getPrice());
            newCabinType.setState(true);
            cabinTypeService.addCabinType(newCabinType);
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(true, "Cabin type created successfully", newCabinType);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the create process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCabinType(@PathVariable("id") Long id, @RequestBody CabinTypeDTO cabinType) {
        try {
            if (id <= 0) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            if (cabinType == null) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Cabin type must not be null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            cabinType.setId(id);
            CabinTypeDTO updatedCabinType = cabinTypeService.updateCabinType(cabinType);
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(true, "Cabin type updated successfully", updatedCabinType);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteCabinType(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinTypeDTO cabinType = cabinTypeService.getCabinTypeById(id);
            if (cabinType == null) {
                ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Cabin type not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            cabinTypeService.deleteCabinType(id);
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(true, "Cabin type deleted successfully", cabinType);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinTypeDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}