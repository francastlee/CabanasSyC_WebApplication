package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.TourDTO;

public interface ITourService {
    
    public List<TourDTO> getAllTours();
    public TourDTO getTourById(Long id);
    public TourDTO addTour(TourDTO tourDTO);
    public TourDTO updateTour(TourDTO tourDTO);
    public void deleteTour(Long id);
    
}
