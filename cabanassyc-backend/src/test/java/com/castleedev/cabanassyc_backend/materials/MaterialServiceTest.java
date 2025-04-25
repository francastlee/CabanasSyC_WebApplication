package com.castleedev.cabanassyc_backend.materials;

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

import com.castleedev.cabanassyc_backend.DAL.IMaterialDAL;
import com.castleedev.cabanassyc_backend.DAL.IMaterialTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialDTO;
import com.castleedev.cabanassyc_backend.Models.Material;
import com.castleedev.cabanassyc_backend.Models.MaterialType;
import com.castleedev.cabanassyc_backend.Services.Implementations.MaterialService;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private IMaterialDAL materialDAL;

    @Mock
    private IMaterialTypeDAL materialTypeDAL;

    @InjectMocks
    private MaterialService materialService;

    private MaterialType testMaterialType;
    private Material testMaterial;
    private MaterialDTO testMaterialDTO;

    @BeforeEach
    void setUp() {
        testMaterialType = new MaterialType(1L, "Construction", true);
        testMaterial = new Material(1L, "Wood", 100, testMaterialType, true);
        testMaterialDTO = new MaterialDTO(1L, "Wood", 100, 1L, true);
    }

    @Test
    void getAllMaterials_WhenMaterialsExist_ReturnsMaterialList() {
        List<Material> mockMaterials = List.of(testMaterial);
        when(materialDAL.findAllByStateTrue()).thenReturn(mockMaterials);

        List<MaterialDTO> result = materialService.getAllMaterials();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Wood");
        verify(materialDAL).findAllByStateTrue();
    }

    @Test
    void getAllMaterials_WhenNoMaterials_ThrowsNotFoundException() {
        when(materialDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> materialService.getAllMaterials())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No materials found");
    }

    @Test
    void getMaterialById_WhenExists_ReturnsMaterialDTO() {
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterial));

        MaterialDTO result = materialService.getMaterialById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Wood");
        assertThat(result.getStock()).isEqualTo(100);
    }

    @Test
    void getMaterialById_WhenNotExists_ThrowsNotFoundException() {
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialService.getMaterialById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Booking not found");
    }

    @Test
    void addMaterial_WithValidData_ReturnsSavedMaterial() {
        when(materialTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterialType));
        when(materialDAL.save(any(Material.class))).thenReturn(testMaterial);

        MaterialDTO result = materialService.addMaterial(testMaterialDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(materialDAL).save(any(Material.class));
    }

    @Test
    void addMaterial_WithNonExistingMaterialType_ThrowsNotFoundException() {
        MaterialDTO newDTO = new MaterialDTO(null, "New Material", 50, 99L, true);
        when(materialTypeDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialService.addMaterial(newDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material type not found");
    }

    @Test
    void updateMaterial_WithValidData_ReturnsUpdatedMaterial() {
        MaterialDTO updateDTO = new MaterialDTO(1L, "Updated Wood", 150, 1L, true);
        
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterial));
        when(materialTypeDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterialType));
        when(materialDAL.save(any(Material.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MaterialDTO result = materialService.updateMaterial(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Wood");
        assertThat(result.getStock()).isEqualTo(150);
        verify(materialDAL).save(any(Material.class));
    }

    @Test
    void updateMaterial_WithNonExistingId_ThrowsNotFoundException() {
        MaterialDTO updateDTO = new MaterialDTO(99L, "Updated Material", 150, 1L, true);
        when(materialDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialService.updateMaterial(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material not found");
    }

    @Test
    void deleteMaterial_WithExistingId_DeletesSuccessfully() {
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterial));
        when(materialDAL.softDeleteById(1L)).thenReturn(1);

        materialService.deleteMaterial(1L);

        verify(materialDAL).softDeleteById(1L);
    }

    @Test
    void deleteMaterial_WithNonExistingId_ThrowsNotFoundException() {
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialService.deleteMaterial(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material not found");
    }

    @Test
    void deleteMaterial_WhenDeleteFails_ThrowsInternalError() {
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterial));
        when(materialDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> materialService.deleteMaterial(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed deleting material");
    }
}