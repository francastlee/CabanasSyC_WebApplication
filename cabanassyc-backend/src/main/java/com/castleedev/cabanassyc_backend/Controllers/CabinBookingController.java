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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cabin-bookings")
public class CabinBookingController {
    
    private final ICabinBookingService cabinBookingService;

    public CabinBookingController(ICabinBookingService cabinBookingService) {
        this.cabinBookingService = cabinBookingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CabinBookingDTO>>> getAllCabinBookings() {
        List<CabinBookingDTO> cabinBookings = cabinBookingService.getAllCabinBookings();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin bookings found successfully", cabinBookings)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CabinBookingDTO>> getCabinBookingById(@PathVariable("id") Long id) {
        CabinBookingDTO cabinBooking = cabinBookingService.getCabinBookingById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin booking found successfully", cabinBooking)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CabinBookingDTO>> addCabinBooking(@Valid @RequestBody CabinBookingDTO cabinBookingDTO) {
        CabinBookingDTO createdBooking = cabinBookingService.addCabinBooking(cabinBookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Cabin booking created successfully", createdBooking));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CabinBookingDTO>> updateCabinBooking(@Valid @RequestBody CabinBookingDTO cabinBookingDTO) {
        CabinBookingDTO updatedBooking = cabinBookingService.updateCabinBooking(cabinBookingDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cabin booking updated successfully", updatedBooking)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCabinBooking(@PathVariable("id") Long id) {
        cabinBookingService.deleteCabinBooking(id);
        return ResponseEntity.ok(
            new ApiResponse<Void>(true, "Cabin booking deleted successfully", null)
        );
    }
}