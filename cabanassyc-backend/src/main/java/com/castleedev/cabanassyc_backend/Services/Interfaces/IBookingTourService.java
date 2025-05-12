package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.BookingTourDTO;

public interface IBookingTourService {
    
    public List<BookingTourDTO> getAllBookingTours();
    public BookingTourDTO getBookingTourById(Long id);
    public BookingTourDTO addBookingTour(BookingTourDTO bookingTourDTO);
    public BookingTourDTO updateBookingTour(BookingTourDTO bookingTourDTO);
    public void deleteBookingTour(Long id);
    
}
