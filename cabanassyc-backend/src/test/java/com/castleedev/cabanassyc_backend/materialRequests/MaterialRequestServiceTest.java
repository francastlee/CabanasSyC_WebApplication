package com.castleedev.cabanassyc_backend.materialRequests;

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
import com.castleedev.cabanassyc_backend.DAL.IMaterialRequestDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialRequestDTO;
import com.castleedev.cabanassyc_backend.Models.Material;
import com.castleedev.cabanassyc_backend.Models.MaterialRequest;
import com.castleedev.cabanassyc_backend.Models.MaterialType;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Implementations.MaterialRequestService;

@ExtendWith(MockitoExtension.class)
class MaterialRequestServiceTest {

    @Mock
    private IMaterialRequestDAL materialRequestDAL;

    @Mock
    private IUserDAL userDAL;

    @Mock
    private IMaterialDAL materialDAL;

    @InjectMocks
    private MaterialRequestService materialRequestService;

    private UserModel testUser;
    private Material testMaterial;
    private MaterialRequest testMaterialRequest;
    private MaterialRequestDTO testMaterialRequestDTO;
    private MaterialType materialType = new MaterialType(1L, "Wood", true);

    @BeforeEach
    void setUp() {
        testUser = new UserModel(1L, "John", "Doe", "john@example.com", "password", 1000, true);
        testMaterial = new Material(1L, "Wood", 7, materialType, true);
        testMaterialRequest = new MaterialRequest(1L, testUser, testMaterial, 10, true);
        testMaterialRequestDTO = new MaterialRequestDTO(1L, 1L, 1L, 10, true);
    }

    @Test
    void getAllMaterialRequests_WhenRequestsExist_ReturnsRequestList() {
        List<MaterialRequest> mockRequests = List.of(testMaterialRequest);
        when(materialRequestDAL.findAllByStateTrue()).thenReturn(mockRequests);

        List<MaterialRequestDTO> result = materialRequestService.getAllMaterialRequests();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getQuantity()).isEqualTo(10);
        verify(materialRequestDAL).findAllByStateTrue();
    }

    @Test
    void getAllMaterialRequests_WhenNoRequests_ThrowsNotFoundException() {
        when(materialRequestDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> materialRequestService.getAllMaterialRequests())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No material requests found");
    }

    @Test
    void getMaterialRequestById_WhenExists_ReturnsRequestDTO() {
        when(materialRequestDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterialRequest));

        MaterialRequestDTO result = materialRequestService.getMaterialRequestById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getMaterialId()).isEqualTo(1L);
    }

    @Test
    void getMaterialRequestById_WhenNotExists_ThrowsNotFoundException() {
        when(materialRequestDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialRequestService.getMaterialRequestById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material request not found");
    }

    @Test
    void addMaterialRequest_WithValidData_ReturnsSavedRequest() {
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterial));
        when(materialRequestDAL.save(any(MaterialRequest.class))).thenReturn(testMaterialRequest);

        MaterialRequestDTO result = materialRequestService.addMaterialRequest(testMaterialRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(materialRequestDAL).save(any(MaterialRequest.class));
    }

    @Test
    void addMaterialRequest_WithNonExistingUser_ThrowsNotFoundException() {
        MaterialRequestDTO newRequest = new MaterialRequestDTO(null, 99L, 1L, 5, true);
        when(userDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialRequestService.addMaterialRequest(newRequest))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User not found");
    }

    @Test
    void addMaterialRequest_WithNonExistingMaterial_ThrowsNotFoundException() {
        MaterialRequestDTO newRequest = new MaterialRequestDTO(null, 1L, 99L, 5, true);
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(materialDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialRequestService.addMaterialRequest(newRequest))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material not found");
    }

    @Test
    void updateMaterialRequest_WithValidData_ReturnsUpdatedRequest() {
        MaterialRequestDTO updateDTO = new MaterialRequestDTO(1L, 1L, 1L, 15, true);
        
        when(materialRequestDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterialRequest));
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testUser));
        when(materialDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterial));
        when(materialRequestDAL.save(any(MaterialRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MaterialRequestDTO result = materialRequestService.updateMaterialRequest(updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(15);
        verify(materialRequestDAL).save(any(MaterialRequest.class));
    }

    @Test
    void updateMaterialRequest_WithNonExistingId_ThrowsNotFoundException() {
        MaterialRequestDTO updateDTO = new MaterialRequestDTO(99L, 1L, 1L, 15, true);
        when(materialRequestDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialRequestService.updateMaterialRequest(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material request not found");
    }

    @Test
    void deleteMaterialRequest_WithExistingId_DeletesSuccessfully() {
        when(materialRequestDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterialRequest));
        when(materialRequestDAL.softDeleteById(1L)).thenReturn(1);

        materialRequestService.deleteMaterialRequest(1L);

        verify(materialRequestDAL).softDeleteById(1L);
    }

    @Test
    void deleteMaterialRequest_WithNonExistingId_ThrowsNotFoundException() {
        when(materialRequestDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> materialRequestService.deleteMaterialRequest(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Material request not found");
    }

    @Test
    void deleteMaterialRequest_WhenDeleteFails_ThrowsInternalError() {
        when(materialRequestDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testMaterialRequest));
        when(materialRequestDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> materialRequestService.deleteMaterialRequest(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete material request");
    }
}
