package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IMaterialDAL;
import com.castleedev.cabanassyc_backend.DAL.IMaterialRequestDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialRequestDTO;
import com.castleedev.cabanassyc_backend.Models.Material;
import com.castleedev.cabanassyc_backend.Models.MaterialRequest;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialRequestService;

@Service
@Transactional
public class MaterialRequestService implements IMaterialRequestService {
    
    private final IMaterialRequestDAL materialRequestDAL;
    private final IUserDAL userDAL;
    private final IMaterialDAL materialDAL;

    public MaterialRequestService(
            IMaterialRequestDAL materialRequestDAL,
            IUserDAL userDAL,
            IMaterialDAL materialDAL) {
        this.materialRequestDAL = materialRequestDAL;
        this.userDAL = userDAL;
        this.materialDAL = materialDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialRequestDTO> getAllMaterialRequests() {
        List<MaterialRequest> materialRequests = materialRequestDAL.findAllByStateTrue();
        if (materialRequests.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No material requests found"
            );  
        }

        return materialRequests.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialRequestDTO getMaterialRequestById(Long id) {
        MaterialRequest materialRequest = materialRequestDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Material request not found"
            ));

        return convertToDTO(materialRequest);
    }

    @Override
    public MaterialRequestDTO addMaterialRequest(MaterialRequestDTO materialRequestDTO) {
        MaterialRequest materialRequest = convertToEntity(materialRequestDTO);
        materialRequest.setState(true);
        MaterialRequest savedMaterialRequest = materialRequestDAL.save(materialRequest);

        return convertToDTO(savedMaterialRequest);
    }

    @Override
    public MaterialRequestDTO updateMaterialRequest(MaterialRequestDTO materialRequestDTO) {
        MaterialRequest existingMaterialRequest = materialRequestDAL.findByIdAndStateTrue(materialRequestDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Material request not found"
            ));
        existingMaterialRequest.setUser(userDAL.findByIdAndStateTrue(materialRequestDTO.getUserId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            )));
        existingMaterialRequest.setMaterial(materialDAL.findByIdAndStateTrue(materialRequestDTO.getMaterialId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Material not found"
            )));
        existingMaterialRequest.setQuantity(materialRequestDTO.getQuantity());
        existingMaterialRequest.setState(materialRequestDTO.isState());
        existingMaterialRequest = materialRequestDAL.save(existingMaterialRequest);

        return convertToDTO(existingMaterialRequest);
    }

    @Override
    public void deleteMaterialRequest(Long id) {
        if (materialRequestDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Material request not found"
            );
        }
        int rowAffected = materialRequestDAL.softDeleteById(id);
        if (rowAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to delete material request"
            );
        }   
    }

    private MaterialRequestDTO convertToDTO(MaterialRequest materialRequest) {
        if (materialRequest == null) return null;
        
        return new MaterialRequestDTO(
            materialRequest.getId(), 
            materialRequest.getUser().getId(), 
            materialRequest.getMaterial().getId(), 
            materialRequest.getQuantity(), 
            materialRequest.isState()
        );
    }

    private MaterialRequest convertToEntity(MaterialRequestDTO materialRequestDTO) {
        if (materialRequestDTO == null) return null;
        
        UserModel user = userDAL.findByIdAndStateTrue(materialRequestDTO.getUserId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));
        Material material = materialDAL.findByIdAndStateTrue(materialRequestDTO.getMaterialId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Material not found"
            ));

        return new MaterialRequest(
            materialRequestDTO.getId(), 
            user,
            material,
            materialRequestDTO.getQuantity(), 
            materialRequestDTO.isState()
        );
    }
}