package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.CabinDTO;
import com.castleedev.cabanassyc_backend.DTO.CabinFullDTO;

public interface ICabinService {

    public List<CabinDTO> getAllCabins();
    public List<CabinFullDTO> getAllCabinsWithDetails();
    public CabinDTO getCabinById(Long id);
    public CabinDTO addCabin(CabinDTO cabin);
    public CabinDTO updateCabin(CabinDTO cabin);
    public void deleteCabin(Long id);
    
} 