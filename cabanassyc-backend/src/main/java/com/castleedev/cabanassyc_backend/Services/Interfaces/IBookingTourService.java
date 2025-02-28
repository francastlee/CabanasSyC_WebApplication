package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.BookingTour;

public interface IBookingTourService {
    
    public List<BookingTour> getAllBookingTours();
    public BookingTour getBookingTourById(Long id);
    public BookingTour addBookingTour(BookingTour bookingTour);
    public BookingTour updateBookingTour(BookingTour bookingTour);
    public void deleteBookingTour(Long id);
    
}
