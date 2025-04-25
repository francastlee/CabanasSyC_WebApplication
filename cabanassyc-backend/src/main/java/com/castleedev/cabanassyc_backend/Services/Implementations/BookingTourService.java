package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.IBookingTourDAL;
import com.castleedev.cabanassyc_backend.DAL.ITourDAL;
import com.castleedev.cabanassyc_backend.DTO.BookingTourDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.BookingTour;
import com.castleedev.cabanassyc_backend.Models.Tour;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingTourService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class BookingTourService implements IBookingTourService {

    private final IBookingTourDAL bookingTourDAL;
    private final IBookingDAL bookingDAL;
    private final ITourDAL tourDAL;

    public BookingTourService(IBookingTourDAL bookingTourDAL,
                            IBookingDAL bookingDAL,
                            ITourDAL tourDAL) {
        this.bookingTourDAL = bookingTourDAL;
        this.bookingDAL = bookingDAL;
        this.tourDAL = tourDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingTourDTO> getAllBookingTours() {
        List<BookingTour> bookingsTour = bookingTourDAL.findAllByStateTrue();
        if (bookingsTour.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No bookings tour found"
            );
        }

        return bookingsTour.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookingTourDTO getBookingTourById(Long id) {
       BookingTour bookingTour = bookingTourDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking tour not found"
            ));
            
        return convertToDTO(bookingTour);
    }

    @Override
    public BookingTourDTO addBookingTour(BookingTourDTO bookingTourDTO) {
        BookingTour bookingTour = convertToEntity(bookingTourDTO);
        bookingTour.setState(true);
        BookingTour savedBookingTour = bookingTourDAL.save(bookingTour);

        return convertToDTO(savedBookingTour);
    }

    @Override
    public BookingTourDTO updateBookingTour(BookingTourDTO bookingTourDTO) {
        BookingTour existing = bookingTourDAL.findByIdAndStateTrue(bookingTourDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking tour not found"
            ));

        existing.setBooking(bookingDAL.findByIdAndStateTrue(bookingTourDTO.getBookingId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            )));
        existing.setTour(tourDAL.findByIdAndStateTrue(bookingTourDTO.getTourId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Tour not found"
            )));
        existing.setPeople(bookingTourDTO.getPeople());
        existing.setState(bookingTourDTO.isState());
        
        BookingTour updated = bookingTourDAL.save(existing);
        return convertToDTO(updated);
    }

    @Override
    public void deleteBookingTour(Long id) {
        if (bookingTourDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking tour not found"
            );
        }
        int rowsAffected = bookingTourDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete booking tour"
            );
        }
    }

    private BookingTourDTO convertToDTO(BookingTour bookingTour) {
        if (bookingTour == null) return null;
        
        return new BookingTourDTO(
            bookingTour.getId(),
            bookingTour.getBooking().getId(),
            bookingTour.getTour().getId(),
            bookingTour.getPeople(),
            bookingTour.isState()
        );
    }

    private BookingTour convertToEntity(BookingTourDTO bookingTourDTO) {
        if (bookingTourDTO == null) return null;

        Booking booking = bookingDAL.findByIdAndStateTrue(bookingTourDTO.getBookingId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Booking not found"
            ));
        
        Tour tour = tourDAL.findByIdAndStateTrue(bookingTourDTO.getTourId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Tour not found"
            ));

        return new BookingTour(
            bookingTourDTO.getId(),
            booking,
            tour,
            bookingTourDTO.getPeople(),
            bookingTourDTO.isState()
        );
    }
}
