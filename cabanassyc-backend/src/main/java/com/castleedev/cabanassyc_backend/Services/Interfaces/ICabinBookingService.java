package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.CabinBooking;

public interface ICabinBookingService {
    
    public List<CabinBooking> getAllCabinBookings();
    public CabinBooking getCabinBookingById(Long id);
    public CabinBooking addCabinBooking(CabinBooking cabinBooking);
    public CabinBooking updateCabinBooking(CabinBooking cabinBooking);
    public void deleteCabinBooking(Long id);
    
}
