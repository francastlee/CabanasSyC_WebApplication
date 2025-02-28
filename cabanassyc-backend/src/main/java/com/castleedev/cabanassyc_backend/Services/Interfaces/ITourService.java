package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.Tour;

public interface ITourService {
    
    public List<Tour> getAllTours();
    public Tour getTourById(Long id);
    public Tour addTour(Tour tour);
    public Tour updateTour(Tour tour);
    public void deleteTour(Long id);
    
}
