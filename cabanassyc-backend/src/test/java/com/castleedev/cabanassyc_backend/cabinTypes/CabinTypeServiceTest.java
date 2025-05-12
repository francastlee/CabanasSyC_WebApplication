package com.castleedev.cabanassyc_backend.cabinTypes;

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

import com.castleedev.cabanassyc_backend.DAL.ICabinTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinTypeDTO;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Implementations.CabinTypeService;

@ExtendWith(MockitoExtension.class)
class CabinTypeServiceTest {

    @Mock
    private ICabinTypeDAL cabinTypeDAL;

    @InjectMocks
    private CabinTypeService cabinTypeService;

    private CabinType testCabinType;
    private CabinTypeDTO testCabinTypeDTO;

    @BeforeEach
    void setUp() {
        testCabinType = new CabinType(1L, "Standard", 4, 100.0, true);
        testCabinTypeDTO = new CabinTypeDTO(1L, "Standard", 4, 100.0, true);
    }

    @Test
    void getAllCabinTypes_WhenTypesExist_ReturnsTypeList() {
        List<CabinType> mockCabinTypes = List.of(testCabinType);
        when(cabinTypeDAL.findAllByStateTrue()).thenReturn(mockCabinTypes);

        List<CabinTypeDTO> result = cabinTypeService.getAllCabinTypes();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(cabinTypeDAL).findAllByStateTrue();
    }

    @Test
    void getAllCabinTypes_WhenNoTypes_ThrowsNotFoundException() {
        when(cabinTypeDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> cabinTypeService.getAllCabinTypes())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No cabin types found");
    }

    @Test
    void getCabinTypeById_WhenExists_ReturnsCabinTypeDTO() {
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinType));

        CabinTypeDTO result = cabinTypeService.getCabinTypeById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Standard");
        assertThat(result.getCapacity()).isEqualTo(4);
    }

    @Test
    void getCabinTypeById_WhenNotExists_ThrowsNotFoundException() {
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinTypeService.getCabinTypeById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin type not found");
    }

    @Test
    void addCabinType_WithValidData_ReturnsSavedCabinType() {
        when(cabinTypeDAL.save(any(CabinType.class))).thenReturn(testCabinType);

        CabinTypeDTO result = cabinTypeService.addCabinType(testCabinTypeDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(cabinTypeDAL).save(any(CabinType.class));
    }

    @Test
    void updateCabinType_WithValidData_ReturnsUpdatedCabinType() {
        CabinTypeDTO updateDTO = new CabinTypeDTO(1L, "Deluxe", 6, 150.0, true);
        
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinType));
        when(cabinTypeDAL.save(any(CabinType.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CabinTypeDTO result = cabinTypeService.updateCabinType(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Deluxe");
        assertThat(result.getCapacity()).isEqualTo(6);
        assertThat(result.getPrice()).isEqualTo(150.0);
        verify(cabinTypeDAL).save(any(CabinType.class));
    }

    @Test
    void updateCabinType_WithNonExistingId_ThrowsNotFoundException() {
        CabinTypeDTO updateDTO = new CabinTypeDTO(99L, "Deluxe", 6, 150.0, true);
        when(cabinTypeDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinTypeService.updateCabinType(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin type not found");
    }

    @Test
    void deleteCabinType_WithExistingId_DeletesSuccessfully() {
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinType));
        when(cabinTypeDAL.softDeleteById(1L)).thenReturn(1);

        cabinTypeService.deleteCabinType(1L);

        verify(cabinTypeDAL).softDeleteById(1L);
    }

    @Test
    void deleteCabinType_WithNonExistingId_ThrowsNotFoundException() {
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cabinTypeService.deleteCabinType(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Cabin type not found");
    }

    @Test
    void deleteCabinType_WhenDeleteFails_ThrowsInternalError() {
        when(cabinTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testCabinType));
        when(cabinTypeDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> cabinTypeService.deleteCabinType(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete cabin type");
    }
}