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

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        try {
            List<BookingDTO> bookings = bookingService.getAllBookings();
            ApiResponse<List<BookingDTO>> apiResponse = new ApiResponse<>(true, "Bookings found successfully", bookings);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            BookingDTO booking = bookingService.getBookingById(id);
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(true, "Booking found successfully", booking);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody BookingDTO booking) {
        try {
            BookingDTO newBooking = new BookingDTO();
            newBooking.setUserId(booking.getUserId());
            newBooking.setDate(booking.getDate());
            newBooking.setTotalPrice(booking.getTotalPrice());
            newBooking.setState(true);
            bookingService.addBooking(newBooking);
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(true, "Booking added successfully", newBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable("id") Long id, @RequestBody BookingDTO booking) {
        try {
            if (id <= 0) {
                ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            booking.setId(id);
            BookingDTO updatedBooking = bookingService.updateBooking(booking);
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(true, "Booking updated successfully", updatedBooking);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            BookingDTO booking = bookingService.getBookingById(id);
            bookingService.deleteBooking(id);
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(true, "Booking deleted successfully", booking);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<BookingDTO> apiResponse = new ApiResponse<>(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
}
