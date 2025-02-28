package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.CabinEquipment;

public interface ICabinEquipmentService {
    
    public List<CabinEquipment> getAllCabinEquipments();
    public CabinEquipment getCabinEquipmentById(Long id);
    public CabinEquipment addCabinEquipment(CabinEquipment cabinEquipment);
    public CabinEquipment updateCabinEquipment(CabinEquipment cabinEquipment);
    public void deleteCabinEquipment(Long id);
    
}
