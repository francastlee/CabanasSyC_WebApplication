package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.MaterialRequestDTO;

public interface IMaterialRequestService {
    
    public List<MaterialRequestDTO> getAllMaterialRequests();
    public MaterialRequestDTO getMaterialRequestById(Long id);
    public MaterialRequestDTO addMaterialRequest(MaterialRequestDTO materialRequestDTO);
    public MaterialRequestDTO updateMaterialRequest(MaterialRequestDTO materialRequestDTO);
    public void deleteMaterialRequest(Long id);
    
}
