package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.ICabinBookingDAL;
import com.castleedev.cabanassyc_backend.Models.CabinBooking;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinBookingService;

@Service
public class CabinBookingService implements ICabinBookingService {
    
    @Autowired
    private ICabinBookingDAL cabinBookingDAL;

    @Override
    public List<CabinBooking> getAllCabinBookings() {
        try {
            return cabinBookingDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabin bookings");
        }   
    }

    @Override
    public CabinBooking getCabinBookingById(Long id) {
        try {
            return cabinBookingDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting cabin booking by id");
        }
    }

    @Override
    public CabinBooking addCabinBooking(CabinBooking cabinBooking) {
        try {
            return cabinBookingDAL.save(cabinBooking);
        } catch (Exception e) {
            throw new RuntimeException("Error adding cabin booking");
        }
    }

    @Override
    public CabinBooking updateCabinBooking(CabinBooking cabinBooking) {
        try {
            return cabinBookingDAL.save(cabinBooking);
        } catch (Exception e) {
            throw new RuntimeException("Error updating cabin booking");
        }
    }

    @Override
    public void deleteCabinBooking(Long id) {
        try {
            cabinBookingDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting cabin booking");
        }
    }

}
