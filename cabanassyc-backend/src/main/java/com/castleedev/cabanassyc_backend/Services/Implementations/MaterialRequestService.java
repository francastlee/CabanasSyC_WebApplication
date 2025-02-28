package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IMaterialRequestDAL;
import com.castleedev.cabanassyc_backend.Models.MaterialRequest;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IMaterialRequestService;

@Service
public class MaterialRequestService implements IMaterialRequestService{
    
    @Autowired
    private IMaterialRequestDAL materialRequestDAL;

    @Override
    public List<MaterialRequest> getAllMaterialRequests() {
        try {
            return materialRequestDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all material requests", e);
        }
    }

    @Override
    public MaterialRequest getMaterialRequestById(Long id) {
        try {
            return materialRequestDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a material request", e);
        }
    }

    @Override
    public MaterialRequest addMaterialRequest(MaterialRequest materialRequest) {
        try {
            return materialRequestDAL.save(materialRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a material request", e);
        }
    }

    @Override
    public MaterialRequest updateMaterialRequest(MaterialRequest materialRequest) {
        try {
            return materialRequestDAL.save(materialRequest);
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