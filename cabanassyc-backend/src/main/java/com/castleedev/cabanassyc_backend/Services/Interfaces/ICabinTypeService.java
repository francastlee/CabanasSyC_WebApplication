package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.CabinType;

public interface ICabinTypeService {
    
    public List<CabinType> getAllCabinTypes();
    public CabinType getCabinTypeById(Long id);
    public CabinType addCabinType(CabinType cabinType);
    public CabinType updateCabinType(CabinType cabinType);
    public void deleteCabinType(Long id);
    
}
