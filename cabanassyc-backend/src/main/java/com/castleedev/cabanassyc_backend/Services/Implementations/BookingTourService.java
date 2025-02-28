package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IBookingTourDAL;
import com.castleedev.cabanassyc_backend.Models.BookingTour;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IBookingTourService;

@Service
public class BookingTourService implements IBookingTourService{

    @Autowired
    private IBookingTourDAL bookingTourDAL;

    @Override
    public List<BookingTour> getAllBookingTours() {
        try {
            return bookingTourDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BookingTour getBookingTourById(Long id) {
        try {
            return bookingTourDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BookingTour addBookingTour(BookingTour bookingTour) {
       try {
            return bookingTourDAL.save(bookingTour);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BookingTour updateBookingTour(BookingTour bookingTour) {
        try {
            return bookingTourDAL.save(bookingTour);
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
