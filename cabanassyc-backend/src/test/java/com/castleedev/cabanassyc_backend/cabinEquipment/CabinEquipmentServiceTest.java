package com.castleedev.cabanassyc_backend.cabinEquipment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinEquipmentDAL;
import com.castleedev.cabanassyc_backend.DAL.IEquipmentDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinEquipmentDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinEquipment;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Models.Equipment;
import com.castleedev.cabanassyc_backend.Services.Implementations.CabinEquipmentService;

@ExtendWith(MockitoExtension.class)
class CabinEquipmentServiceTest {

    @Mock
    private ICabinEquipmentDAL cabinEquipmentDAL;

    @Mock
    private ICabinDAL cabinDAL;

    @Mock
    private IEquipmentDAL equipmentDAL;

    @InjectMocks
    private CabinEquipmentService cabinEquipmentService;

    private Cabin testCabin;
    private Equipment testEquipment;
    private CabinEquipment testCabinEquipment;
    private CabinEquipmentDTO testCabinEquipmentDTO;
    private CabinType testCabinType = new CabinType(1L, "Standard", 2, 150.0, true);


    @BeforeEach
    void setUp() {
        testCabin = new Cabin(1L, "Cabin 1", testCabinType, true);
        testEquipment = new Equipment(1L, "TV", true);
        testCabinEquipment = new CabinEquipment(1L, testCabin, testEquipment, true);
        testCabinEquipmentDTO = new CabinEquipmentDTO(1L, 1L, 1L, true);
    }

    @Test
    void getAllCabinEquipments_WhenEquipmentsExist_ReturnsEquipmentList() {
        List<CabinEquipment> mockCabinEquipments = List.of(testCabinEquipment);
        when(cabinEquipmentDAL.findAllByStateTrue()).thenReturn(mockCabinEquipments);

        List<CabinEquipmentDTO> result = cabinEquipmentService.getAllCabinEquipments();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(cabinEquipmentDAL).findAllByStateTrue();
    }

    @Test
    void getAllCabinEquipments_WhenNoEquipments_ThrowsNotFoundException() {
        when(cabinEquipmentDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> cabinEquipmentService.getAllCabinEquipments())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No cabin equipment found");
    }

    @Test
    void getCabinEquipmentById_WhenExists_ReturnsCabinEquipmentDTO() {
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinEquipment));

        CabinEquipmentDTO result = cabinEquipmentService.getCabinEquipmentById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCabinId()).isEqualTo(1L);
        assertThat(result.getEquipmentId()).isEqualTo(1L);
    }

    @Test
    void getCabinEquipmentById_WhenNotExists_ThrowsNotFoundException() {
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinEquipmentService.getCabinEquipmentById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin equipment not found");
    }

    @Test
    void addCabinEquipment_WithValidData_ReturnsSavedCabinEquipment() {
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testEquipment));
        when(cabinEquipmentDAL.save(any(CabinEquipment.class))).thenReturn(testCabinEquipment);

        CabinEquipmentDTO result = cabinEquipmentService.addCabinEquipment(testCabinEquipmentDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(cabinEquipmentDAL).save(any(CabinEquipment.class));
    }

    @Test
    void addCabinEquipment_WithNonExistingCabin_ThrowsNotFoundException() {
        CabinEquipmentDTO newDTO = new CabinEquipmentDTO(null, 99L, 1L, true);
        when(cabinDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinEquipmentService.addCabinEquipment(newDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin not found");
    }

    @Test
    void addCabinEquipment_WithNonExistingEquipment_ThrowsNotFoundException() {
        CabinEquipmentDTO newDTO = new CabinEquipmentDTO(null, 1L, 99L, true);
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(equipmentDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinEquipmentService.addCabinEquipment(newDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Equipment not found");
    }

    @Test
    void updateCabinEquipment_WithValidData_ReturnsUpdatedCabinEquipment() {
        CabinEquipmentDTO updateDTO = new CabinEquipmentDTO(1L, 1L, 1L, false);
        
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinEquipment));
        when(cabinDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabin));
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testEquipment));
        when(cabinEquipmentDAL.save(any(CabinEquipment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CabinEquipmentDTO result = cabinEquipmentService.updateCabinEquipment(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.isState()).isFalse();
        verify(cabinEquipmentDAL).save(any(CabinEquipment.class));
    }

    @Test
    void updateCabinEquipment_WithNonExistingId_ThrowsNotFoundException() {
        CabinEquipmentDTO updateDTO = new CabinEquipmentDTO(99L, 1L, 1L, true);
        when(cabinEquipmentDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinEquipmentService.updateCabinEquipment(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin equipment not found");
    }

    @Test
    void updateCabinEquipment_WithNonExistingCabin_ThrowsNotFoundException() {
        CabinEquipmentDTO updateDTO = new CabinEquipmentDTO(1L, 99L, 1L, true);
        
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinEquipment));
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testEquipment)); // Equipment existe
        when(cabinDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty()); // Cabin no existe

        assertThatThrownBy(() -> cabinEquipmentService.updateCabinEquipment(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin not found")
            .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);
        
        verify(equipmentDAL).findByIdAndStateTrue(1L);
        verify(cabinDAL).findByIdAndStateTrue(99L);
        verify(cabinEquipmentDAL, never()).save(any());
    }
    
    @Test
    void updateCabinEquipment_WithNonExistingEquipment_ThrowsNotFoundException() {
        CabinEquipmentDTO updateDTO = new CabinEquipmentDTO(1L, 1L, 99L, true);
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinEquipment));
        when(equipmentDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinEquipmentService.updateCabinEquipment(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Equipment not found");
    }

    @Test
    void deleteCabinEquipment_WithExistingId_DeletesSuccessfully() {
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinEquipment));
        when(cabinEquipmentDAL.softDeleteById(1L)).thenReturn(1);

        cabinEquipmentService.deleteCabinEquipment(1L);

        verify(cabinEquipmentDAL).softDeleteById(1L);
    }

    @Test
    void deleteCabinEquipment_WithNonExistingId_ThrowsNotFoundException() {
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinEquipmentService.deleteCabinEquipment(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin equipment not found");
    }

    @Test
    void deleteCabinEquipment_WhenDeleteFails_ThrowsInternalError() {
        when(cabinEquipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinEquipment));
        when(cabinEquipmentDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> cabinEquipmentService.deleteCabinEquipment(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete cabin equipment");
    }
}