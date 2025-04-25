package com.castleedev.cabanassyc_backend.cabins;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Implementations.CabinService;

@ExtendWith(MockitoExtension.class)
class CabinServiceTest {

    @Mock
    private ICabinDAL cabinDAL;

    @Mock
    private ICabinTypeDAL cabinTypeDAL;

    @InjectMocks
    private CabinService cabinService;

    private Cabin testCabin;
    private CabinType testCabinType;
    private CabinDTO testCabinDTO;

    @BeforeEach
    void setUp() {
        testCabinType = new CabinType(1L, "Standard", 3, 10.0, true);
        testCabin = new Cabin(1L, "Cabin 1", testCabinType, true);
        testCabinDTO = new CabinDTO(1L, "Cabin 1", 1L, true);
    }

    @Test
    void getAllCabins_WhenCabinsExist_ReturnsCabinList() {
        List<Cabin> mockCabins = List.of(testCabin);
        when(cabinDAL.findAllByStateTrue()).thenReturn(mockCabins);

        List<CabinDTO> result = cabinService.getAllCabins();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(cabinDAL).findAllByStateTrue();
    }

    @Test
    void getAllCabins_WhenNoCabins_ThrowsNotFoundException() {
        when(cabinDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> cabinService.getAllCabins())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No cabins found");
    }

    @Test
    void getCabinById_WhenExists_ReturnsCabinDTO() {
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));

        CabinDTO result = cabinService.getCabinById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Cabin 1");
    }

    @Test
    void getCabinById_WhenNotExists_ThrowsNotFoundException() {
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinService.getCabinById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin not found");
    }

    @Test
    void addCabin_WithValidData_ReturnsSavedCabin() {
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinType));
        when(cabinDAL.save(any(Cabin.class))).thenReturn(testCabin);

        CabinDTO result = cabinService.addCabin(testCabinDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(cabinDAL).save(any(Cabin.class));
    }

    @Test
    void addCabin_WithNonExistingCabinType_ThrowsNotFoundException() {
        CabinDTO newDTO = new CabinDTO(null, "New Cabin", 99L, true);
        when(cabinTypeDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinService.addCabin(newDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin type not found");
    }

    @Test
    void updateCabin_WithValidData_ReturnsUpdatedCabin() {
        CabinDTO updateDTO = new CabinDTO(1L, "Updated Cabin", 1L, true);
        
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinType));
        when(cabinDAL.save(any(Cabin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CabinDTO result = cabinService.updateCabin(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Cabin");
        verify(cabinDAL).save(any(Cabin.class));
    }

    @Test
    void updateCabin_WithNonExistingId_ThrowsNotFoundException() {
        CabinDTO updateDTO = new CabinDTO(99L, "Updated Cabin", 1L, true);
        when(cabinDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinService.updateCabin(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin not found");
    }

    @Test
    void updateCabin_WithNonExistingCabinType_ThrowsNotFoundException() {
        CabinDTO updateDTO = new CabinDTO(1L, "Updated Cabin", 99L, true);
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(cabinTypeDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinService.updateCabin(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin type not found");
    }

    @Test
    void deleteCabin_WithExistingId_DeletesSuccessfully() {
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(cabinDAL.softDeleteById(1L)).thenReturn(1);

        cabinService.deleteCabin(1L);

        verify(cabinDAL).softDeleteById(1L);
    }

    @Test
    void deleteCabin_WithNonExistingId_ThrowsNotFoundException() {
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinService.deleteCabin(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin not found");
    }

    @Test
    void deleteCabin_WhenDeleteFails_ThrowsInternalError() {
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(cabinDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> cabinService.deleteCabin(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed deleting cabin");
    }
}