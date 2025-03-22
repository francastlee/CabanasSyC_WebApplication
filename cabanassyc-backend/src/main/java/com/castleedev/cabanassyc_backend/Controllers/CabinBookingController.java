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

import com.castleedev.cabanassyc_backend.DTO.CabinBookingDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinBookingService;

@RestController
@RequestMapping("/cabinbookings")
public class CabinBookingController {
    
    private final ICabinBookingService cabinBookingService;

    public CabinBookingController(ICabinBookingService cabinBookingService) {
        this.cabinBookingService = cabinBookingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCabinBookings() {
        try {
            List<CabinBookingDTO> cabinBookings = cabinBookingService.getAllCabinBookings();
            if (cabinBookings.isEmpty()) {
                ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "No cabin bookings found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<CabinBookingDTO>> apiResponse = new ApiResponse<>(true, "Cabin bookings found successfully", cabinBookings);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCabinBookingById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinBookingDTO cabinBooking = cabinBookingService.getCabinBookingById(id);
            if (cabinBooking == null) {
                ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Cabin booking not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(true, "Cabin booking found successfully", cabinBooking);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addCabinBooking(@RequestBody CabinBookingDTO cabinBooking) {
        try {
            CabinBookingDTO newCabinBooking = new CabinBookingDTO();
            newCabinBooking.setBookingId(cabinBooking.getBookingId());
            newCabinBooking.setCabinId(cabinBooking.getCabinId());
            newCabinBooking.setAdultsQuantity(cabinBooking.getAdultsQuantity());
            newCabinBooking.setChildrenQuantity(cabinBooking.getChildrenQuantity());
            newCabinBooking.setState(true);
            cabinBookingService.addCabinBooking(newCabinBooking);
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(true, "Cabin booking added successfully", newCabinBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCabinBooking(@PathVariable("id") Long id, @RequestBody CabinBookingDTO cabinBooking) {
        try {
            if (id <= 0) {
                ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            cabinBooking.setId(id);
            CabinBookingDTO updatedCabinBooking = cabinBookingService.updateCabinBooking(cabinBooking);
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(true, "Cabin booking updated successfully", updatedCabinBooking);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteCabinBooking(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            CabinBookingDTO cabinBooking = cabinBookingService.getCabinBookingById(id);
            if (cabinBooking == null) {
                ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Cabin booking not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            cabinBookingService.deleteCabinBooking(id);
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(true, "Cabin booking deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<CabinBookingDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}