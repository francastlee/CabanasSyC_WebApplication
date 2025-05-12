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

import com.castleedev.cabanassyc_backend.DTO.TourDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ITourService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tours")
public class TourController {
    
    private final ITourService tourService;

    public TourController(ITourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TourDTO>>> getAllTours() {
        List<TourDTO> tours = tourService.getAllTours();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Tours found successfully", tours)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TourDTO>> getTourById(@PathVariable("id") Long id) {
        TourDTO tour = tourService.getTourById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Tour found successfully", tour)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TourDTO>> addTour(@Valid @RequestBody TourDTO tourDTO) {
        TourDTO createdTour = tourService.addTour(tourDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Tour created successfully", createdTour));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<TourDTO>> updateTour(@Valid @RequestBody TourDTO tourDTO) {
        TourDTO updatedTour = tourService.updateTour(tourDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Tour updated successfully", updatedTour)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTour(@PathVariable("id") Long id) {
        tourService.deleteTour(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Tour deleted successfully", null)
        );
    }
}