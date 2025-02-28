package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.Booking;

public interface IBookingService {
    
    public List<Booking> getAllBookings();
    public Booking getBookingById(Long id);
    public Booking addBooking(Booking booking);
    public Booking updateBooking(Booking booking);
    public void deleteBooking(Long id);
    
}
