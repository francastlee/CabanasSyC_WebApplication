package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.ITourDAL;
import com.castleedev.cabanassyc_backend.DTO.TourDTO;
import com.castleedev.cabanassyc_backend.Models.Tour;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ITourService;
import java.util.ArrayList;

@Service
public class TourService implements ITourService{
    
    @Autowired
    private ITourDAL tourDAL;

    TourDTO convertir (Tour tour) {
        return new TourDTO(
            tour.getId(), 
            tour.getName(), 
            tour.getCapacity(), 
            tour.getPrice(), 
            tour.getStartTime(), 
            tour.getEndTime(), 
            tour.isState()
        );
    }

    Tour convertir (TourDTO tourDTO) {
        return new Tour(
            tourDTO.getId(), 
            tourDTO.getName(), 
            tourDTO.getCapacity(), 
            tourDTO.getPrice(), 
            tourDTO.getStartTime(), 
            tourDTO.getEndTime(), 
            tourDTO.isState()
        );
    }

    @Override
    public List<TourDTO> getAllTours() {
        try {
            List<Tour> tours = tourDAL.findAllByStateTrue();
            List<TourDTO> toursDTO = new ArrayList<TourDTO>();
            for (Tour tour : tours) {
                toursDTO.add(convertir(tour));
            }
            return toursDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all tours: " + e.getMessage());
        }
    }

    @Override
    public TourDTO getTourById(Long id) {
        try {
            Tour tour = tourDAL.findByIdAndStateTrue(id);
            return convertir(tour);
        } catch (Exception e) {
            throw new RuntimeException("Error getting tour by id", e);
        }
    }

    @Override
    public TourDTO addTour(TourDTO tourDTO) {
        try {
            Tour tour = convertir(tourDTO);
            return convertir(tourDAL.save(tour));
        } catch (Exception e) {
            throw new RuntimeException("Error adding tour", e);
        }
    }

    @Override
    public TourDTO updateTour(TourDTO tourDTO) {
        try {
            Tour tour = convertir(tourDTO);
            return convertir(tourDAL.save(tour));
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