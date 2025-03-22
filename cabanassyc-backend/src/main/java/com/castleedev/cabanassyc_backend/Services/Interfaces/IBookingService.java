package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.BookingDTO;

public interface IBookingService {
    
    public List<BookingDTO> getAllBookings();
    public BookingDTO getBookingById(Long id);
    public BookingDTO addBooking(BookingDTO bookingDTO);
    public BookingDTO updateBooking(BookingDTO bookingDTO);
    public void deleteBooking(Long id);
    
}
