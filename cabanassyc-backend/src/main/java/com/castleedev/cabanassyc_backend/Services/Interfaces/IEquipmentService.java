package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.DTO.EquipmentDTO;

public interface IEquipmentService {

    public List<EquipmentDTO> getAllEquipments();
    public EquipmentDTO getEquipmentById(Long id);
    public EquipmentDTO addEquipment(EquipmentDTO equipmentDTO);
    public EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO);
    public void deleteEquipment(Long id);
    
}
