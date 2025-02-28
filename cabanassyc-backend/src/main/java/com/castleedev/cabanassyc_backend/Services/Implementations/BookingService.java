package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingService;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingDAL bookingDAL;

    @Override
    public List<Booking> getAllBookings() {
        try {
            return bookingDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all bookings");
        }
    }

    @Override
    public Booking getBookingById(Long id) {
        try {
            return bookingDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a booking");
        }
    }

    @Override
    public Booking addBooking(Booking booking) {
        try {
            return bookingDAL.save(booking);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a booking");
        }
    }

    @Override
    public Booking updateBooking(Booking booking) {
        try {
            return bookingDAL.save(booking);
        } catch (Exception e) {
            throw new RuntimeException("Error updating a booking");
        }
    }

    @Override
    public void deleteBooking(Long id) {
        try {
            bookingDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a booking");
        }
    }

    
}
