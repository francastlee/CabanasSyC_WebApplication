package com.castleedev.cabanassyc_backend.equipments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.argThat;
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

import com.castleedev.cabanassyc_backend.DAL.IEquipmentDAL;
import com.castleedev.cabanassyc_backend.DTO.EquipmentDTO;
import com.castleedev.cabanassyc_backend.Models.Equipment;
import com.castleedev.cabanassyc_backend.Services.Implementations.EquipmentService;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    private IEquipmentDAL equipmentDAL;

    @InjectMocks
    private EquipmentService equipmentService;

    private Equipment testEquipment;
    private EquipmentDTO testEquipmentDTO;

    @BeforeEach
    void setUp() {
        testEquipment = new Equipment(1L, "TV", true);
        testEquipmentDTO = new EquipmentDTO(1L, "TV", true);
    }

    @Test
    void getAllEquipments_WhenEquipmentsExist_ReturnsEquipmentList() {
        List<Equipment> mockEquipments = List.of(testEquipment);
        when(equipmentDAL.findAllByStateTrue()).thenReturn(mockEquipments);

        List<EquipmentDTO> result = equipmentService.getAllEquipments();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("TV");
        verify(equipmentDAL).findAllByStateTrue();
    }

    @Test
    void getAllEquipments_WhenNoEquipments_ThrowsNotFoundException() {
        when(equipmentDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> equipmentService.getAllEquipments())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No equipments found");
    }

    @Test
    void getEquipmentById_WhenExists_ReturnsEquipmentDTO() {
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testEquipment));

        EquipmentDTO result = equipmentService.getEquipmentById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("TV");
    }

    @Test
    void getEquipmentById_WhenNotExists_ThrowsNotFoundException() {
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> equipmentService.getEquipmentById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Equipment not found");
    }

    @Test
    void addEquipment_WithValidData_ReturnsSavedEquipment() {
        when(equipmentDAL.save(any(Equipment.class))).thenReturn(testEquipment);

        EquipmentDTO result = equipmentService.addEquipment(testEquipmentDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(equipmentDAL).save(argThat(equipment -> 
            equipment.isState() && equipment.getName().equals("TV")
        ));
    }

    @Test
    void updateEquipment_WithValidData_ReturnsUpdatedEquipment() {
        EquipmentDTO updateDTO = new EquipmentDTO(1L, "Updated TV", false);
        
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testEquipment));
        when(equipmentDAL.save(any(Equipment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EquipmentDTO result = equipmentService.updateEquipment(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated TV");
        assertThat(result.isState()).isFalse();
        verify(equipmentDAL).save(any(Equipment.class));
    }

    @Test
    void updateEquipment_WithNonExistingId_ThrowsNotFoundException() {
        EquipmentDTO updateDTO = new EquipmentDTO(99L, "Updated TV", true);
        when(equipmentDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> equipmentService.updateEquipment(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Equipment not found");
    }

    @Test
    void deleteEquipment_WithExistingId_DeletesSuccessfully() {
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testEquipment));
        when(equipmentDAL.softDeleteById(1L)).thenReturn(1);

        equipmentService.deleteEquipment(1L);

        verify(equipmentDAL).softDeleteById(1L);
    }

    @Test
    void deleteEquipment_WithNonExistingId_ThrowsNotFoundException() {
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> equipmentService.deleteEquipment(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Equipment not found");
    }

    @Test
    void deleteEquipment_WhenDeleteFails_ThrowsInternalError() {
        when(equipmentDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testEquipment));
        when(equipmentDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> equipmentService.deleteEquipment(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete equipment");
    }
}
