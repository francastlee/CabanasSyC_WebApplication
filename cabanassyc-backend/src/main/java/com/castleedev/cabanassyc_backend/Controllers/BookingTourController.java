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

import com.castleedev.cabanassyc_backend.DTO.BookingTourDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingTourService;

@RestController
@RequestMapping("/bookingtours")
public class BookingTourController {
    
    private final IBookingTourService bookingTourService;

    public BookingTourController(IBookingTourService bookingTourService) {
        this.bookingTourService = bookingTourService;
    }
    
    @GetMapping
    public ResponseEntity<?> getAllBookingTours() {
        try {
            List<BookingTourDTO> bookingTours = bookingTourService.getAllBookingTours();
            if (bookingTours.isEmpty()) {
                ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "No booking tours found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<BookingTourDTO>> apiResponse = new ApiResponse<>(true, "Booking tours found successfully", bookingTours);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingTourById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            BookingTourDTO bookingTour = bookingTourService.getBookingTourById(id);
            if (bookingTour == null) {
                ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Booking tour not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(true, "Booking tour found successfully", bookingTour);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Error during the get process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBookingTour(@RequestBody BookingTourDTO bookingTour) {
        try {
            BookingTourDTO newBookingTour = new BookingTourDTO();
            newBookingTour.setBookingId(bookingTour.getBookingId());
            newBookingTour.setTourId(bookingTour.getTourId());
            newBookingTour.setPeople(bookingTour.getPeople());
            newBookingTour.setState(true);
            bookingTourService.addBookingTour(newBookingTour);
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(true, "Booking tour added successfully", newBookingTour);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookingTour(@PathVariable("id") Long id, @RequestBody BookingTourDTO bookingTour) {
        try {
            if (id <= 0) {
                ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            bookingTour.setId(id);
            BookingTourDTO updatedBookingTour = bookingTourService.updateBookingTour(bookingTour);
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(true, "Booking tour updated successfully", updatedBookingTour);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteBookingTour(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            BookingTourDTO bookingTour = bookingTourService.getBookingTourById(id);
            if (bookingTour == null) {
                ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Booking tour not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            bookingTourService.deleteBookingTour(id);
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(true, "Booking tour deleted successfully", bookingTour);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingTourDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

}