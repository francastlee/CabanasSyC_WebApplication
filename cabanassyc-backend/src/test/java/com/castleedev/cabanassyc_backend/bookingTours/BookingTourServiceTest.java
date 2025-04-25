package com.castleedev.cabanassyc_backend.bookingTours;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.time.LocalDate;
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

import com.castleedev.cabanassyc_backend.DAL.IBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.IBookingTourDAL;
import com.castleedev.cabanassyc_backend.DAL.ITourDAL;
import com.castleedev.cabanassyc_backend.DTO.BookingTourDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.BookingTour;
import com.castleedev.cabanassyc_backend.Models.Tour;
import com.castleedev.cabanassyc_backend.Services.Implementations.BookingTourService;

@ExtendWith(MockitoExtension.class)
class BookingTourServiceTest {

    @Mock
    private IBookingTourDAL bookingTourDAL;

    @Mock
    private IBookingDAL bookingDAL;

    @Mock
    private ITourDAL tourDAL;

    @InjectMocks
    private BookingTourService bookingTourService;

    private Booking testBooking;
    private Tour testTour;
    private BookingTour testBookingTour;
    private BookingTourDTO testBookingTourDTO;

    @BeforeEach
    void setUp() {
        testBooking = new Booking(1L, null, LocalDate.now(), 100.0, true);
        testTour = new Tour(1L, "Tour Test", 30, 50.0, Time.valueOf("10:00:00"), Time.valueOf("12:00:00"), true);
        testBookingTour = new BookingTour(1L, testBooking, testTour, 2, true);
        testBookingTourDTO = new BookingTourDTO(1L, 1L, 1L, 2, true);
    }

    @Test
    void getAllBookingTours_WhenBookingsExist_ReturnsBookingTourList() {
        List<BookingTour> mockBookingTours = List.of(testBookingTour);
        when(bookingTourDAL.findAllByStateTrue()).thenReturn(mockBookingTours);

        List<BookingTourDTO> result = bookingTourService.getAllBookingTours();

        assertThat(result).isNotEmpty();        
        verify(bookingTourDAL).findAllByStateTrue();
    }

    @Test
    void getAllBookingTours_WhenNoBookings_ThrowsNotFoundException() {
        when(bookingTourDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> bookingTourService.getAllBookingTours())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No bookings tour found");
    }

    @Test
    void getBookingTourById_WhenBookingExists_ReturnsBookingTourDTO() {
        when(bookingTourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBookingTour));

        BookingTourDTO result = bookingTourService.getBookingTourById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getBookingId()).isEqualTo(1L);
        assertThat(result.getTourId()).isEqualTo(1L);
    }

    @Test
    void getBookingTourById_WhenBookingNotExists_ThrowsNotFoundException() {
        when(bookingTourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingTourService.getBookingTourById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking tour not found");
    }

    @Test
    void addBookingTour_WithValidData_ReturnsNewBookingTour() {
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testTour));
        when(bookingTourDAL.save(any(BookingTour.class))).thenReturn(testBookingTour);

        BookingTourDTO result = bookingTourService.addBookingTour(testBookingTourDTO);

        assertThat(result.getId()).isEqualTo(1L);
        verify(bookingTourDAL).save(any(BookingTour.class));
    }

    @Test
    void addBookingTour_WithNonExistingBooking_ThrowsNotFoundException() {
        BookingTourDTO newBookingTour = new BookingTourDTO(null, 99L, 1L, 2, true);
        when(bookingDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingTourService.addBookingTour(newBookingTour))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking not found");
    }

    @Test
    void addBookingTour_WithNonExistingTour_ThrowsNotFoundException() {
        BookingTourDTO newBookingTour = new BookingTourDTO(null, 1L, 99L, 2, true);
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(tourDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingTourService.addBookingTour(newBookingTour))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Tour not found");
    }

    @Test
    void updateBookingTour_WithValidData_ReturnsUpdatedBookingTour() {
        BookingTourDTO updateDTO = new BookingTourDTO(1L, 1L, 1L, 3, true);
        when(bookingTourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBookingTour));
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(tourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testTour));
        when(bookingTourDAL.save(any(BookingTour.class))).thenReturn(testBookingTour);

        BookingTourDTO result = bookingTourService.updateBookingTour(updateDTO);
        
        assertThat(result.getPeople()).isEqualTo(3);
        verify(bookingTourDAL).save(any(BookingTour.class));
    }

    @Test
    void updateBookingTour_WithNonExistingId_ThrowsNotFoundException() {
        BookingTourDTO updateDTO = new BookingTourDTO(99L, 1L, 1L, 2, true);
        when(bookingTourDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingTourService.updateBookingTour(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking tour not found");
    }

    @Test
    void deleteBookingTour_WithExistingId_DeletesSuccessfully() {
        when(bookingTourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBookingTour));
        when(bookingTourDAL.softDeleteById(1L)).thenReturn(1);

        bookingTourService.deleteBookingTour(1L);

        verify(bookingTourDAL).softDeleteById(1L);
    }

    @Test
    void deleteBookingTour_WithNonExistingId_ThrowsNotFoundException() {
        when(bookingTourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingTourService.deleteBookingTour(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking tour not found");
    }

    @Test
    void deleteBookingTour_WhenDeleteFails_ThrowsInternalError() {
        when(bookingTourDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBookingTour));
        when(bookingTourDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> bookingTourService.deleteBookingTour(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete booking tour");
    }
}