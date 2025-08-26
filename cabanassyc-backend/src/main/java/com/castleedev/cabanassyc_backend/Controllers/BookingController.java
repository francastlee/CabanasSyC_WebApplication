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

import com.castleedev.cabanassyc_backend.DTO.BookingDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Bookings found successfully", bookings)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDTO>> getBookingById(@PathVariable("id") Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Booking found successfully", booking)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingDTO>> addBooking(@Valid @RequestBody BookingDTO booking) {
        BookingDTO newBooking = bookingService.addBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "Booking added successfully", newBooking));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<BookingDTO>> updateBooking(@Valid @RequestBody BookingDTO booking) {
        BookingDTO updatedBooking = bookingService.updateBooking(booking);
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Booking updated successfully", updatedBooking)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(@PathVariable("id") Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(
            new ApiResponse<Void>(true, "Booking deleted successfully", null)
        );
    }
}