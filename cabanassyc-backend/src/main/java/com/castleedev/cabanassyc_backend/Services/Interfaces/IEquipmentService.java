package com.castleedev.cabanassyc_backend.Services.Interfaces;

import java.util.List;

import com.castleedev.cabanassyc_backend.Models.Equipment;

public interface IEquipmentService {

    public List<Equipment> getAllEquipments();
    public Equipment getEquipmentById(Long id);
    public Equipment addEquipment(Equipment equipment);
    public Equipment updateEquipment(Equipment equipment);
    public void deleteEquipment(Long id);
    
}
