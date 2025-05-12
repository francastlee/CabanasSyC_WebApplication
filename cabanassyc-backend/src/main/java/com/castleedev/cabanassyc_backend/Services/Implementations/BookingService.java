package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.BookingDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingService;

@Service
@Transactional
public class BookingService implements IBookingService {

    private final IBookingDAL bookingDAL;
    private final IUserDAL userDAL;

    public BookingService(IBookingDAL bookingDAL, IUserDAL userDAL) {
        this.bookingDAL = bookingDAL;
        this.userDAL = userDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingDAL.findAllByStateTrue();
        if (bookings.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No bookings found"
            );
        }

        return bookings.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            ));

        return convertToDTO(booking);
    }

    @Override
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        booking.setState(true);
        Booking savedBooking = bookingDAL.save(booking);
        
        return convertToDTO(savedBooking);
    }

    @Override
    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        Booking existingBooking = bookingDAL.findByIdAndStateTrue(bookingDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            ));
        existingBooking.setDate(bookingDTO.getDate());
        existingBooking.setTotalPrice(bookingDTO.getTotalPrice());
        existingBooking.setState(bookingDTO.isState());
        Booking updatedBooking = bookingDAL.save(existingBooking);

        return convertToDTO(updatedBooking);
    }

    @Override
    public void deleteBooking(Long id) {
        if (bookingDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            );
        }
        int rowsAffected = bookingDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete booking"
            );
        }    
    }

    private BookingDTO convertToDTO(Booking booking) {
        if (booking == null) return null;
        
        return new BookingDTO(
            booking.getId(),
            booking.getUser().getId(),
            booking.getDate(),
            booking.getTotalPrice(),
            booking.isState()
        );
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        if (bookingDTO == null) return null;
        
        UserModel user = userDAL.findByIdAndStateTrue(bookingDTO.getUserId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "User not found"
            ));

        return new Booking(
            bookingDTO.getId(),
            user,
            bookingDTO.getDate(),
            bookingDTO.getTotalPrice(),
            bookingDTO.isState()
        );
    }
}