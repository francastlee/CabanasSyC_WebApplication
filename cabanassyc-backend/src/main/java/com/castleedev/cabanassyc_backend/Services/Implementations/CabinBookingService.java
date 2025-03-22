package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinBookingDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinBooking;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinBookingService;
import java.util.ArrayList;

@Service
public class CabinBookingService implements ICabinBookingService {
    
    @Autowired
    private ICabinBookingDAL cabinBookingDAL;

    @Autowired
    private IBookingDAL bookingDAL;

    @Autowired
    private ICabinDAL cabinDAL;

    CabinBookingDTO convertir (CabinBooking cabinBooking) {
        return new CabinBookingDTO(
            cabinBooking.getId(), 
            cabinBooking.getBooking().getId(), 
            cabinBooking.getCabin().getId(), 
            cabinBooking.getAdultsQuantity(), 
            cabinBooking.getChildrenQuantity(), 
            cabinBooking.isState()
        );
    }

    CabinBooking convertir (CabinBookingDTO cabinBookingDTO) {
        Cabin cabin = cabinDAL.findByIdAndStateTrue(cabinBookingDTO.getCabinId());
        if (cabin == null) {
            throw new RuntimeException("Cabin not found");
        }
        Booking booking = bookingDAL.findByIdAndStateTrue(cabinBookingDTO.getBookingId());
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        return new CabinBooking(
            cabinBookingDTO.getId(), 
            cabin,
            booking, 
            cabinBookingDTO.getAdultsQuantity(), 
            cabinBookingDTO.getChildrenQuantity(), 
            cabinBookingDTO.isState()
        );
    }

    @Override
    public List<CabinBookingDTO> getAllCabinBookings() {
        try {
            List<CabinBooking> cabinBookings = cabinBookingDAL.findAllByStateTrue();
            List<CabinBookingDTO> cabinBookingsDTO = new ArrayList<CabinBookingDTO>();
            for (CabinBooking cabinBooking : cabinBookings) {
                cabinBookingsDTO.add(convertir(cabinBooking));
            }
            return cabinBookingsDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabin bookings: " + e.getMessage());
        }   
    }

    @Override
    public CabinBookingDTO getCabinBookingById(Long id) {
        try {
            CabinBooking cabinBooking = cabinBookingDAL.findByIdAndStateTrue(id);
            if (cabinBooking == null) {
                throw new RuntimeException("Cabin booking not found");
            }
            return convertir(cabinBooking);
        } catch (Exception e) {
            throw new RuntimeException("Error getting cabin booking by id");
        }
    }

    @Override
    public CabinBookingDTO addCabinBooking(CabinBookingDTO cabinBooking) {
        try {
            CabinBooking cabinBookingModel = convertir(cabinBooking);
            return convertir(cabinBookingDAL.save(cabinBookingModel));
        } catch (Exception e) {
            throw new RuntimeException("Error adding cabin booking");
        }
    }

    @Override
    public CabinBookingDTO updateCabinBooking(CabinBookingDTO cabinBooking) {
        try {
            CabinBooking cabinBookingModel = convertir(cabinBooking);
            return convertir(cabinBookingDAL.save(cabinBookingModel));
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
