package com.castleedev.cabanassyc_backend.tours;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.ITourDAL;
import com.castleedev.cabanassyc_backend.DTO.TourDTO;
import com.castleedev.cabanassyc_backend.Models.Tour;
import com.castleedev.cabanassyc_backend.Services.Implementations.TourService;
/* 
@ExtendWith(MockitoExtension.class)
class TourServiceTest {

    @Mock
    private ITourDAL tourDAL;

    @InjectMocks
    private TourService tourService;

    private Tour testTour;
    private TourDTO testTourDTO;

    @BeforeEach
    void setUp() {
        testTour = new Tour(
            1L, 
            "Sunset Tour", 
            20, 
            50.0, 
            Time.valueOf("16:00:00"), 
            Time.valueOf("18:00:00"), 
            true
        );
        
        testTourDTO = new TourDTO(
            1L, 
            "Sunset Tour", 
            20, 
            50.0, 
            Time.valueOf("16:00:00"), 
            Time.valueOf("18:00:00"), 
            true
        );
    }

    @Test
    void getAllTours_WhenToursExist_ReturnsTourList() {
        List<Tour> mockTours = List.of(testTour);
        when(tourDAL.findAllByStateTrue()).thenReturn(mockTours);

        List<TourDTO> result = tourService.getAllTours();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Sunset Tour");
        verify(tourDAL).findAllByStateTrue();
    }

    @Test
    void getAllTours_WhenNoTours_ThrowsNotFoundException() {
        when(tourDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> tourService.getAllTours())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No tours found");
    }

    @Test
    void getTourById_WhenExists_ReturnsTourDTO() {
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testTour));

        TourDTO result = tourService.getTourById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Sunset Tour");
        assertThat(result.getCapacity()).isEqualTo(20);
    }

    @Test
    void getTourById_WhenNotExists_ThrowsNotFoundException() {
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tourService.getTourById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Tour not found");
    }

    @Test
    void addTour_WithValidData_ReturnsSavedTour() {
        when(tourDAL.save(any(Tour.class))).thenReturn(testTour);

        TourDTO result = tourService.addTour(testTourDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(tourDAL).save(argThat(tour -> 
            tour.isState() && 
            tour.getName().equals("Sunset Tour") &&
            tour.getStartTime().equals(Time.valueOf("16:00:00"))
        ));
    }

    @Test
    void updateTour_WithValidData_ReturnsUpdatedTour() {
        TourDTO updateDTO = new TourDTO(
            1L, "Updated Tour", 25, 60.0, 
            Time.valueOf("17:00:00"), Time.valueOf("19:00:00"), true
        );
        
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testTour));
        when(tourDAL.save(any(Tour.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TourDTO result = tourService.updateTour(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Tour");
        assertThat(result.getCapacity()).isEqualTo(25);
        assertThat(result.getPrice()).isEqualTo(60.0);
        verify(tourDAL).save(any(Tour.class));
    }

    @Test
    void updateTour_WithNonExistingId_ThrowsNotFoundException() {
        TourDTO updateDTO = new TourDTO(
            99L, "Updated Tour", 25, 60.0, 
            Time.valueOf("17:00:00"), Time.valueOf("19:00:00"), true
        );
        when(tourDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tourService.updateTour(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Tour not found");
    }

    @Test
    void deleteTour_WithExistingId_DeletesSuccessfully() {
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testTour));
        when(tourDAL.softDeleteById(1L)).thenReturn(1);

        tourService.deleteTour(1L);

        verify(tourDAL).softDeleteById(1L);
    }

    @Test
    void deleteTour_WithNonExistingId_ThrowsNotFoundException() {
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tourService.deleteTour(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Tour not found");
    }

    @Test
    void deleteTour_WhenDeleteFails_ThrowsInternalError() {
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testTour));
        when(tourDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> tourService.deleteTour(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete tour");
    }
}
*/