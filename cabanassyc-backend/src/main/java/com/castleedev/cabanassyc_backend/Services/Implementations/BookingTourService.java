package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.IBookingTourDAL;
import com.castleedev.cabanassyc_backend.DAL.ITourDAL;
import com.castleedev.cabanassyc_backend.DTO.BookingTourDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.BookingTour;
import com.castleedev.cabanassyc_backend.Models.Tour;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingTourService;
import java.util.ArrayList;

@Service
public class BookingTourService implements IBookingTourService{

    @Autowired
    private IBookingTourDAL bookingTourDAL;

    @Autowired
    private IBookingDAL bookingDAL;

    @Autowired
    private ITourDAL tourDAL;

    BookingTourDTO convertir (BookingTour bookingTour){
        return new BookingTourDTO(
            bookingTour.getId(), 
            bookingTour.getBooking().getId(), 
            bookingTour.getTour().getId(), 
            bookingTour.getPeople(), 
            bookingTour.isState()
        );
    }

    BookingTour convertir (BookingTourDTO bookingTourDTO){
        Booking booking = bookingDAL.findByIdAndStateTrue(bookingTourDTO.getBookingId());
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        Tour tour = tourDAL.findByIdAndStateTrue(bookingTourDTO.getTourId());
        if (tour == null) {
            throw new RuntimeException("Tour not found");
        }
        return new BookingTour(
            bookingTourDTO.getId(), 
            booking,
            tour,
            bookingTourDTO.getPeople(), 
            bookingTourDTO.isState()
        );
    }

    @Override
    public List<BookingTourDTO> getAllBookingTours() {
        try {
            List<BookingTour> bookingTours = bookingTourDAL.findAllByStateTrue();
            List<BookingTourDTO> bookingToursDTO = new ArrayList<BookingTourDTO>();
            for (BookingTour bookingTour : bookingTours) {
                bookingToursDTO.add(convertir(bookingTour));
            }
            return bookingToursDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BookingTourDTO getBookingTourById(Long id) {
        try {
            BookingTour bookingTour = bookingTourDAL.findByIdAndStateTrue(id);
            if (bookingTour == null) {
                throw new RuntimeException("BookingTour not found");
            }
            return convertir(bookingTour);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BookingTourDTO addBookingTour(BookingTourDTO bookingTourDTO) {
       try {
            BookingTour bookingTour = convertir(bookingTourDTO);
            return convertir(bookingTourDAL.save(bookingTour));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BookingTourDTO updateBookingTour(BookingTourDTO bookingTourDTO) {
        try {
            BookingTour bookingTour = convertir(bookingTourDTO);
            return convertir(bookingTourDAL.save(bookingTour));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteBookingTour(Long id) {
        try {
            bookingTourDAL.softDeleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
