package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinBookingDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinBooking;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinBookingService;


@Service
@Transactional
public class CabinBookingService implements ICabinBookingService {

    private final ICabinBookingDAL cabinBookingDAL;
    private final IBookingDAL bookingDAL;
    private final ICabinDAL cabinDAL;

    public CabinBookingService(ICabinBookingDAL cabinBookingDAL,
                             IBookingDAL bookingDAL,
                             ICabinDAL cabinDAL) {
        this.cabinBookingDAL = cabinBookingDAL;
        this.bookingDAL = bookingDAL;
        this.cabinDAL = cabinDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CabinBookingDTO> getAllCabinBookings() {
        List<CabinBooking> cabinBookings = cabinBookingDAL.findAllByStateTrue();
        if (cabinBookings.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No cabin bookings found"
            );
        }

        return cabinBookings.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CabinBookingDTO getCabinBookingById(Long id) {
        CabinBooking cabinBooking = cabinBookingDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin booking not found"
            ));

        return convertToDTO(cabinBooking);
    }

    @Override
    public CabinBookingDTO addCabinBooking(CabinBookingDTO cabinBookingDTO) {
        CabinBooking cabinBooking = convertToEntity(cabinBookingDTO);
        cabinBooking.setState(true);
        CabinBooking savedBooking = cabinBookingDAL.save(cabinBooking);

        return convertToDTO(savedBooking);
    }

    @Override
    public CabinBookingDTO updateCabinBooking(CabinBookingDTO cabinBookingDTO) {
        CabinBooking existingBooking = cabinBookingDAL.findByIdAndStateTrue(cabinBookingDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin booking not found"
            ));

        existingBooking.setCabin(cabinDAL.findByIdAndStateTrue(cabinBookingDTO.getCabinId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin not found"
            )));
        existingBooking.setBooking(bookingDAL.findByIdAndStateTrue(cabinBookingDTO.getBookingId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            )));
        existingBooking.setAdultsQuantity(cabinBookingDTO.getAdultsQuantity());
        existingBooking.setChildrenQuantity(cabinBookingDTO.getChildrenQuantity());
        existingBooking.setState(cabinBookingDTO.isState());

        existingBooking = cabinBookingDAL.save(existingBooking);
        
        return convertToDTO(existingBooking);
    }

    @Override
    public void deleteCabinBooking(Long id) {
        if (cabinBookingDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin booking not found"
            );
        }
        int rowsAffected = cabinBookingDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed deleting cabin booking"
            );
        }
    }

    private CabinBookingDTO convertToDTO(CabinBooking cabinBooking) {
        if (cabinBooking == null) return null;
        
        return new CabinBookingDTO(
            cabinBooking.getId(),
            cabinBooking.getBooking().getId(),
            cabinBooking.getCabin().getId(),
            cabinBooking.getAdultsQuantity(),
            cabinBooking.getChildrenQuantity(),
            cabinBooking.isState()
        );
    }

    private CabinBooking convertToEntity(CabinBookingDTO cabinBookingDTO) {
        if (cabinBookingDTO == null) return null;

        Booking booking = bookingDAL.findByIdAndStateTrue(cabinBookingDTO.getBookingId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            ));
        Cabin cabin = cabinDAL.findByIdAndStateTrue(cabinBookingDTO.getCabinId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin not found"
            ));
        CabinBooking cabinBooking = new CabinBooking();
        cabinBooking.setId(cabinBookingDTO.getId());
        cabinBooking.setBooking(booking);
        cabinBooking.setCabin(cabin);
        cabinBooking.setAdultsQuantity(cabinBookingDTO.getAdultsQuantity());
        cabinBooking.setChildrenQuantity(cabinBookingDTO.getChildrenQuantity());
        cabinBooking.setState(cabinBookingDTO.isState());
        
        return cabinBooking;

    }
}