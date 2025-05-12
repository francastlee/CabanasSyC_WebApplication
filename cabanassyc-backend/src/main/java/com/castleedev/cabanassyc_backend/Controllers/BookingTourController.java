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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking-tours")
public class BookingTourController {
    
    private final IBookingTourService bookingTourService;

    public BookingTourController(IBookingTourService bookingTourService) {
        this.bookingTourService = bookingTourService;
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingTourDTO>>> getAllBookingTours() {
        List<BookingTourDTO> bookingTours = bookingTourService.getAllBookingTours();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Booking tours found successfully", bookingTours)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingTourDTO>> getBookingTourById(@PathVariable("id") Long id) {
        BookingTourDTO bookingTour = bookingTourService.getBookingTourById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Booking tour found successfully", bookingTour)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingTourDTO>> addBookingTour(@Valid @RequestBody BookingTourDTO bookingTourDTO) {
        BookingTourDTO createdBookingTour = bookingTourService.addBookingTour(bookingTourDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Booking tour created successfully", createdBookingTour));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<BookingTourDTO>> updateBookingTour(@Valid @RequestBody BookingTourDTO bookingTourDTO) {
        BookingTourDTO updatedBookingTour = bookingTourService.updateBookingTour(bookingTourDTO);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Booking tour updated successfully", updatedBookingTour)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBookingTour(@PathVariable("id") Long id) {
        bookingTourService.deleteBookingTour(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Booking tour deleted successfully", null)
        );
    }
}