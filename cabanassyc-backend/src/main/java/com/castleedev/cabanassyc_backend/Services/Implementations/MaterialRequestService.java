package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IMaterialDAL;
import com.castleedev.cabanassyc_backend.DAL.IMaterialRequestDAL;
import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DTO.MaterialRequestDTO;
import com.castleedev.cabanassyc_backend.Models.Material;
import com.castleedev.cabanassyc_backend.Models.MaterialRequest;
import com.castleedev.cabanassyc_backend.Models.User;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialRequestService;
import java.util.ArrayList;

@Service
public class MaterialRequestService implements IMaterialRequestService{
    
    @Autowired
    private IMaterialRequestDAL materialRequestDAL;

    @Autowired
    private IUserDAL userDAL;

    @Autowired
    private IMaterialDAL materialDAL;

    MaterialRequestDTO convertir (MaterialRequest materialRequest) {
        return new MaterialRequestDTO(
            materialRequest.getId(), 
            materialRequest.getUser().getId(), 
            materialRequest.getMaterial().getId(), 
            materialRequest.getQuantity(), 
            materialRequest.isState()
        ); 
    }

    MaterialRequest convertir (MaterialRequestDTO materialRequestDTO) {
        User user = userDAL.findByIdAndStateTrue(materialRequestDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Material material = materialDAL.findByIdAndStateTrue(materialRequestDTO.getMaterialId());
        if (material == null) {
            throw new RuntimeException("Material not found");
        }
        return new MaterialRequest(
            materialRequestDTO.getId(), 
            user,
            material,
            materialRequestDTO.getQuantity(), 
            materialRequestDTO.isState()
        ); 
    }

    @Override
    public List<MaterialRequestDTO> getAllMaterialRequests() {
        try {
            List<MaterialRequest> materialRequests = materialRequestDAL.findAllByStateTrue();
            List<MaterialRequestDTO> materialRequestsDTO = new ArrayList<MaterialRequestDTO>();
            for (MaterialRequest materialRequest : materialRequests) {
                materialRequestsDTO.add(convertir(materialRequest));
            }
            return materialRequestsDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all material requests", e);
        }
    }

    @Override
    public MaterialRequestDTO getMaterialRequestById(Long id) {
        try {
            MaterialRequest materialRequest = materialRequestDAL.findByIdAndStateTrue(id);
            if (materialRequest == null) {
                throw new RuntimeException("Material request not found");
            }
            return convertir(materialRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a material request", e);
        }
    }

    @Override
    public MaterialRequestDTO addMaterialRequest(MaterialRequestDTO materialRequestDTO) {
        try {
            MaterialRequest materialRequest = convertir(materialRequestDTO);
            return convertir(materialRequestDAL.save(materialRequest));
        } catch (Exception e) {
            throw new RuntimeException("Error adding a material request", e);
        }
    }

    @Override
    public MaterialRequestDTO updateMaterialRequest(MaterialRequestDTO materialRequestDTO) {
        try {
            MaterialRequest materialRequest = convertir(materialRequestDTO);
            return convertir(materialRequestDAL.save(materialRequest));
        } catch (Exception e) {
            throw new RuntimeException("Error updating a material request", e);
        }
    }

    @Override
    public void deleteMaterialRequest(Long id) {
        try {
            materialRequestDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a material request", e);
        }
    }
    
}