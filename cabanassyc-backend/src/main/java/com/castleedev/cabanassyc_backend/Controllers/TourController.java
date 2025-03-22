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

@RestController
@RequestMapping("/tours")
public class TourController {
    
    private final ITourService tourService;

    public TourController(ITourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTours() {
        try {
            List<TourDTO> tours = tourService.getAllTours();
            if (tours.isEmpty()) {
                ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "No tours found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<TourDTO>> apiResponse = new ApiResponse<>(true, "Tours found successfully", tours);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            TourDTO tour = tourService.getTourById(id);
            if (tour == null) {
                ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Tour not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(true, "Tour found successfully", tour);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addTour(@RequestBody TourDTO tour) {
        try {
            TourDTO newTour = new TourDTO();
            newTour.setName(tour.getName());
            newTour.setCapacity(tour.getCapacity());
            newTour.setPrice(tour.getPrice());
            newTour.setStartTime(tour.getStartTime());
            newTour.setEndTime(tour.getEndTime());
            newTour.setState(true);
            tourService.addTour(newTour);
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(true, "Tour added successfully", newTour);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTour(@PathVariable("id") Long id, @RequestBody TourDTO tour) {
        try {
            if (id <= 0) {
                ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            tour.setId(id);
            TourDTO updatedTour = tourService.updateTour(tour);
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(true, "Tour updated successfully", updatedTour);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteTour(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            TourDTO tour = tourService.getTourById(id);
            if (tour == null) {
                ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Tour not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            tourService.deleteTour(id);
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(true, "Tour deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<TourDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
}
