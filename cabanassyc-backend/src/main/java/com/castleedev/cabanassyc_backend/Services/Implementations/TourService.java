package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.ITourDAL;
import com.castleedev.cabanassyc_backend.Models.Tour;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ITourService;

@Service
public class TourService implements ITourService{
    
    @Autowired
    private ITourDAL tourDAL;

    @Override
    public List<Tour> getAllTours() {
        try {
            return tourDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all tours", e);
        }
    }

    @Override
    public Tour getTourById(Long id) {
        try {
            return tourDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting tour by id", e);
        }
    }

    @Override
    public Tour addTour(Tour tour) {
        try {
            return tourDAL.save(tour);
        } catch (Exception e) {
            throw new RuntimeException("Error adding tour", e);
        }
    }

    @Override
    public Tour updateTour(Tour tour) {
        try {
            return tourDAL.save(tour);
        } catch (Exception e) {
            throw new RuntimeException("Error updating tour", e);
        }
    }

    @Override
    public void deleteTour(Long id) {
        try {
            tourDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting tour", e);
        }
    }

}