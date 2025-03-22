package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.BookingDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.User;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingService;
import java.util.ArrayList;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingDAL bookingDAL;

    @Autowired
    private IUserDAL userDAL;

    BookingDTO convert (Booking booking) {
        return new BookingDTO(
            booking.getId(), 
            booking.getUser().getId(), 
            booking.getDate(), 
            booking.getTotalPrice(), 
            booking.isState()
        );
    }

    Booking convert (BookingDTO bookingDTO) {
        User user = userDAL.findByIdAndStateTrue(bookingDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new Booking(
            bookingDTO.getId(), 
            user, 
            bookingDTO.getDate(), 
            bookingDTO.getTotalPrice(), 
            bookingDTO.isState()
        );
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        try {
            List<Booking> bookings = bookingDAL.findAllByStateTrue();
            if (bookings.isEmpty()) {
                throw new RuntimeException("No bookings found");
            }
            List<BookingDTO> bookingsDTO = new ArrayList<BookingDTO>();
            for (Booking booking : bookings) {
                bookingsDTO.add(convert(booking));
            }
            return bookingsDTO;      
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during the get process: " + e.getMessage()); 
        } catch (Exception e) {
            throw new RuntimeException("Error getting a bookings: " + e.getMessage());
        }
    }

    @Override
    public BookingDTO getBookingById(Long id) {
        try {
            Booking booking = bookingDAL.findByIdAndStateTrue(id);
            if (booking == null) {
                throw new RuntimeException("Booking not found");
            }
            return convert(booking);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during the get process: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error getting a booking: " + e.getMessage());
        }
    }

    @Override
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = convert(bookingDTO);
            return convert(bookingDAL.save(booking));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during the post process: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error creating a booking: " + e.getMessage());
        }
    }

    @Override
    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = bookingDAL.findByIdAndStateTrue(bookingDTO.getId());
            if (booking == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
            }
            Booking bookingConvert = convert(bookingDTO);
            return convert(bookingDAL.save(bookingConvert));
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during the put process: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error creating a booking: " + e.getMessage());
        }
    }

    @Override
    public void deleteBooking(Long id) {
        try {
            Booking booking = bookingDAL.findByIdAndStateTrue(id);
            if (booking == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
            }
            bookingDAL.softDeleteById(booking.getId());
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during the delete process: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a booking: " + e.getMessage());
        }
    }
    
}
