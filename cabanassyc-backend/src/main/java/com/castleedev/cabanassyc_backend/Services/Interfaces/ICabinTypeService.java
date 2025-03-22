package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.CabinTypeDTO;

public interface ICabinTypeService {
    
    public List<CabinTypeDTO> getAllCabinTypes();
    public CabinTypeDTO getCabinTypeById(Long id);
    public CabinTypeDTO addCabinType(CabinTypeDTO cabinType);
    public CabinTypeDTO updateCabinType(CabinTypeDTO cabinType);
    public void deleteCabinType(Long id);
    
}
