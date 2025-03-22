package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.CabinBookingDTO;
public interface ICabinBookingService {
    
    public List<CabinBookingDTO> getAllCabinBookings();
    public CabinBookingDTO getCabinBookingById(Long id);
    public CabinBookingDTO addCabinBooking(CabinBookingDTO cabinBookingDTO);
    public CabinBookingDTO updateCabinBooking(CabinBookingDTO cabinBookingDTO);
    public void deleteCabinBooking(Long id);
    
}
