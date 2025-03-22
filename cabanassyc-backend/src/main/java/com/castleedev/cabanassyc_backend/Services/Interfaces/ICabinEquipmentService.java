package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.CabinEquipmentDTO;
public interface ICabinEquipmentService {
    
    public List<CabinEquipmentDTO> getAllCabinEquipments();
    public CabinEquipmentDTO getCabinEquipmentById(Long id);
    public CabinEquipmentDTO addCabinEquipment(CabinEquipmentDTO cabinEquipmentDTO);
    public CabinEquipmentDTO updateCabinEquipment(CabinEquipmentDTO cabinEquipmentDTO);
    public void deleteCabinEquipment(Long id);
    
}
