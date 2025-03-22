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
import com.castleedev.cabanassyc_backend.Models.Contact;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinService;

@RestController
@RequestMapping("/cabins")
public class CabinController {
    
    private final ICabinService cabinService;

    public CabinController(ICabinService cabinService) {
        this.cabinService = cabinService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCabins() {
        try {
            List<CabinDTO> cabins = cabinService.getAllCabins();
            if (cabins.isEmpty()) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "No cabins found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<CabinDTO>> apiResponse = new ApiResponse<>(true, "Cabins found successfully", cabins);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCabinById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinDTO cabin = cabinService.getCabinById(id);
            if (cabin == null) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Cabin not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(true, "Cabin found successfully", cabin);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCabin (@RequestBody CabinDTO cabin) {
        try {
            if (cabin.getName() == null || cabin.getCabinTypeId() == null) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Cabin name and type are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            
            CabinDTO newCabin = new CabinDTO();
            newCabin.setName(cabin.getName());
            newCabin.setCabinTypeId(cabin.getCabinTypeId());
            newCabin.setState(true);
            cabinService.addCabin(newCabin);

            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(true, "Cabin added successfully", newCabin);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCabin (@PathVariable("id") Long id, @RequestBody CabinDTO cabin){
        try {
            if (id <= 0) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            if (cabin.getName() == null || cabin.getCabinTypeId() == null) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Cabin name and type are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            cabin.setId(id);
            cabinService.updateCabin(cabin);
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(true, "Cabin updated successfully", cabin);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
        
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteCabin (@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<Contact> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinDTO cabin = cabinService.getCabinById(id);
            if (cabin == null) {
                ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, "Cabin not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            
            cabinService.deleteCabin(id);
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(true, "Cabin deleted successfully", cabin);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinDTO> apiResponse = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
}
