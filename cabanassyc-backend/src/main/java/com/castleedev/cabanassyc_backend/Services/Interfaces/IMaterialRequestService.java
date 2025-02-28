package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.MaterialRequest;

public interface IMaterialRequestService {
    
    public List<MaterialRequest> getAllMaterialRequests();
    public MaterialRequest getMaterialRequestById(Long id);
    public MaterialRequest addMaterialRequest(MaterialRequest materialRequest);
    public MaterialRequest updateMaterialRequest(MaterialRequest materialRequest);
    public void deleteMaterialRequest(Long id);
    
}
