package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.ITourDAL;
import com.castleedev.cabanassyc_backend.DAL.ITourImageDAL;
import com.castleedev.cabanassyc_backend.DTO.TourDTO;
import com.castleedev.cabanassyc_backend.DTO.TourImageDTO;
import com.castleedev.cabanassyc_backend.Models.Tour;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ITourService;

@Service
@Transactional
public class TourService implements ITourService {
    
    private final ITourDAL tourDAL;
    private final ITourImageDAL tourImageDAL;

    public TourService(ITourDAL tourDAL, ITourImageDAL tourImageDAL) {
    this.tourDAL = tourDAL;
    this.tourImageDAL = tourImageDAL;
}

    @Override
    @Transactional(readOnly = true)
    public List<TourDTO> getAllTours() {
        List<Tour> tours = tourDAL.findAllByStateTrue();
        if (tours.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No tours found"
            );
        }
        
        return tours.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TourDTO getTourById(Long id) {
        Tour tour = tourDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Tour not found"
            ));

        return convertToDTO(tour);
    }

    @Override
    public TourDTO addTour(TourDTO tourDTO) {
        Tour tour = convertToEntity(tourDTO);
        tour.setState(true);
        Tour savedTour = tourDAL.save(tour);

        return convertToDTO(savedTour);
    }

    @Override
    public TourDTO updateTour(TourDTO tourDTO) {
        Tour existingTour = tourDAL.findByIdAndStateTrue(tourDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Tour not found"
            ));
        existingTour.setName(tourDTO.getName());
        existingTour.setCapacity(tourDTO.getCapacity());
        existingTour.setPrice(tourDTO.getPrice());
        existingTour.setStartTime(tourDTO.getStartTime());
        existingTour.setEndTime(tourDTO.getEndTime());
        existingTour.setState(tourDTO.isState());
        existingTour = tourDAL.save(existingTour);

        return convertToDTO(existingTour);
    }

    @Override
    public void deleteTour(Long id) {
        if (tourDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Tour not found"
            );
        }
        int rowsAffected = tourDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete tour"
            );
        }
    }

    private TourDTO convertToDTO(Tour tour) {
        if (tour == null) return null;

        List<TourImageDTO> images = tourImageDAL.findAllByTourIdAndStateTrue(tour.getId())
            .stream()
            .map(image -> new TourImageDTO(image.getUrl(), image.isCover()))
            .collect(Collectors.toList());

        return new TourDTO(
            tour.getId(),
            tour.getName(),
            tour.getCapacity(),
            tour.getPrice(),
            tour.getStartTime(),
            tour.getEndTime(),
            tour.isState(),
            images
        );
    }

    private Tour convertToEntity(TourDTO tourDTO) {
        if (tourDTO == null) return null;
        
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
}