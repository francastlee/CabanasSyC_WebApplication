package com.castleedev.cabanassyc_backend.cabinBooking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.castleedev.cabanassyc_backend.DAL.ICabinBookingDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinBookingDTO;
import com.castleedev.cabanassyc_backend.Models.Booking;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinBooking;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Implementations.CabinBookingService;

@ExtendWith(MockitoExtension.class)
class CabinBookingServiceTest {

    @Mock
    private ICabinBookingDAL cabinBookingDAL;

    @Mock
    private IBookingDAL bookingDAL;

    @Mock
    private ICabinDAL cabinDAL;

    @InjectMocks
    private CabinBookingService cabinBookingService;

    private Booking testBooking;
    private Cabin testCabin;
    private CabinBooking testCabinBooking;
    private CabinBookingDTO testCabinBookingDTO;
    private CabinType testCabinType = new CabinType(1L, "Type 1", 4, 2.0, true);

    @BeforeEach
    void setUp() {
        testBooking = new Booking(1L, null, LocalDate.now(), 100.0, true);
        testCabin = new Cabin(1L, "Cabin 1", testCabinType, true);
        testCabinBooking = new CabinBooking(1L, testCabin, testBooking, 2, 1, true);
        testCabinBookingDTO = new CabinBookingDTO(1L, 1L, 1L, 2, 1, true);
    }

    @Test
    void getAllCabinBookings_WhenBookingsExist_ReturnsBookingList() {
        List<CabinBooking> mockBookings = List.of(testCabinBooking);
        when(cabinBookingDAL.findAllByStateTrue()).thenReturn(mockBookings);

        List<CabinBookingDTO> result = cabinBookingService.getAllCabinBookings();

        assertThat(result).isNotEmpty();        
        verify(cabinBookingDAL).findAllByStateTrue();
    }

    @Test
    void getAllCabinBookings_WhenNoBookings_ThrowsNotFoundException() {
        when(cabinBookingDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> cabinBookingService.getAllCabinBookings())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No cabin bookings found");
    }

    @Test
    void getCabinBookingById_WhenBookingExists_ReturnsBookingDTO() {
        when(cabinBookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinBooking));

        CabinBookingDTO result = cabinBookingService.getCabinBookingById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getBookingId()).isEqualTo(1L);
        assertThat(result.getCabinId()).isEqualTo(1L);
    }

    @Test
    void getCabinBookingById_WhenBookingNotExists_ThrowsNotFoundException() {
        when(cabinBookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinBookingService.getCabinBookingById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin booking not found");
    }

    @Test
    void addCabinBooking_WithValidData_ReturnsNewBooking() {
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(cabinBookingDAL.save(any(CabinBooking.class))).thenReturn(testCabinBooking);

        CabinBookingDTO result = cabinBookingService.addCabinBooking(testCabinBookingDTO);

        assertThat(result.getId()).isEqualTo(1L);
        verify(cabinBookingDAL).save(any(CabinBooking.class));
    }

    @Test
    void addCabinBooking_WithNonExistingBooking_ThrowsNotFoundException() {
        CabinBookingDTO newBooking = new CabinBookingDTO(null, 99L, 1L, 2, 1, true);
        when(bookingDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinBookingService.addCabinBooking(newBooking))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking not found");
    }

    @Test
    void addCabinBooking_WithNonExistingCabin_ThrowsNotFoundException() {
        CabinBookingDTO newBooking = new CabinBookingDTO(null, 1L, 99L, 2, 1, true);
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(cabinDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinBookingService.addCabinBooking(newBooking))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin not found");
    }

    @Test
    void updateCabinBooking_WithValidData_ReturnsUpdatedBooking() {
        CabinBookingDTO updateDTO = new CabinBookingDTO(1L, 1L, 1L, 3, 2, true);
        when(cabinBookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinBooking));
        when(bookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testBooking));
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(cabinBookingDAL.save(any(CabinBooking.class))).thenReturn(testCabinBooking);

        CabinBookingDTO result = cabinBookingService.updateCabinBooking(updateDTO);

        assertThat(result.getAdultsQuantity()).isEqualTo(3);
        assertThat(result.getChildrenQuantity()).isEqualTo(2);
        verify(cabinBookingDAL).save(any(CabinBooking.class));
    }

    @Test
    void updateCabinBooking_WithNonExistingId_ThrowsNotFoundException() {
        CabinBookingDTO updateDTO = new CabinBookingDTO(99L, 1L, 1L, 2, 1, true);
        when(cabinBookingDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinBookingService.updateCabinBooking(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin booking not found");
    }

    @Test
    void updateCabinBooking_WithNonExistingCabin_ThrowsNotFoundException() {
        CabinBookingDTO updateDTO = new CabinBookingDTO(1L, 1L, 99L, 2, 1, true);
        when(cabinBookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinBooking));
        when(cabinDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinBookingService.updateCabinBooking(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin not found");
    }

    @Test
    void deleteCabinBooking_WithExistingId_DeletesSuccessfully() {
        when(cabinBookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinBooking));
        when(cabinBookingDAL.softDeleteById(1L)).thenReturn(1);

        cabinBookingService.deleteCabinBooking(1L);

        verify(cabinBookingDAL).softDeleteById(1L);
    }

    @Test
    void deleteCabinBooking_WithNonExistingId_ThrowsNotFoundException() {
        when(cabinBookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinBookingService.deleteCabinBooking(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin booking not found");
    }

    @Test
    void deleteCabinBooking_WhenDeleteFails_ThrowsInternalError() {
        when(cabinBookingDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinBooking));
        when(cabinBookingDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> cabinBookingService.deleteCabinBooking(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed deleting cabin booking");
    }
}