package com.castleedev.cabanassyc_backend.bookings;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

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
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.BookingDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Implementations.BookingService;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private IBookingDAL bookingDAL;

    @Mock
    private IUserDAL userDAL;

    @InjectMocks
    private BookingService bookingService;

    private UserModel testUser;
    private Booking testBooking;
    private BookingDTO testBookingDTO;

    @BeforeEach
    void setUp() {
        testUser = new UserModel(1L, "test", "test", "user@example.com", "password", 1500, true);
        testBooking = new Booking(1L, testUser, LocalDate.now().plusDays(7), 150.0, true);
        testBookingDTO = new BookingDTO(1L, 1L, LocalDate.now().plusDays(7), 150.0, true);
    }

    @Test
    void getAllBookings_WhenBookingsExist_ReturnsBookingList() {
        List<Booking> mockBookings = List.of(testBooking);
        when(bookingDAL.findAllByStateTrue()).thenReturn(mockBookings);

        List<BookingDTO> result = bookingService.getAllBookings();

        assertThat(result).isNotEmpty();        
        verify(bookingDAL).findAllByStateTrue();
    }

    @Test
    void getAllBookings_WhenNoBookings_ThrowsNotFoundException() {
        when(bookingDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> bookingService.getAllBookings())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No bookings found");
    }

    @Test
    void getBookingById_WhenBookingExists_ReturnsBookingDTO() {
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));

        BookingDTO result = bookingService.getBookingById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
    }

    @Test
    void getBookingById_WhenBookingNotExists_ThrowsNotFoundException() {
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.getBookingById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking not found");
    }

    @Test
    void updateBooking_WithValidData_ReturnsUpdatedBooking() {
        BookingDTO updateDTO = new BookingDTO(1L, 1L, LocalDate.now().plusDays(10), 175.0, true);
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(bookingDAL.save(any(Booking.class))).thenReturn(testBooking);

        BookingDTO result = bookingService.updateBooking(updateDTO);

        assertThat(result.getTotalPrice()).isEqualTo(175.0);
        verify(bookingDAL).save(any(Booking.class));
    }

    @Test
    void updateBooking_WithNonExistingId_ThrowsNotFoundException() {
        BookingDTO updateDTO = new BookingDTO(99L, 1L, LocalDate.now(), 100.0, true);
        when(bookingDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.updateBooking(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking not found");
    }

    @Test
    void deleteBooking_WithExistingId_DeletesSuccessfully() {
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(bookingDAL.softDeleteById(1L)).thenReturn(1);

        bookingService.deleteBooking(1L);

        verify(bookingDAL).softDeleteById(1L);
    }

    @Test
    void deleteBooking_WithNonExistingId_ThrowsNotFoundException() {
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.deleteBooking(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking not found");
    }

    @Test
    void deleteBooking_WhenDeleteFails_ThrowsInternalError() {
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(bookingDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> bookingService.deleteBooking(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete booking");
    }

    @Test
    void addBooking_WithValidData_ReturnsNewBooking() {
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(bookingDAL.save(any(Booking.class))).thenReturn(testBooking);

        BookingDTO result = bookingService.addBooking(testBookingDTO);

        assertThat(result.getId()).isEqualTo(1L);
        verify(bookingDAL).save(any(Booking.class));
    }

    @Test
    void addBooking_WithNonExistingUser_ThrowsNotFoundException() {
        BookingDTO newBooking = new BookingDTO(null, 99L, LocalDate.now(), 100.0, true);
        when(userDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.addBooking(newBooking))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User not found");
    }
}