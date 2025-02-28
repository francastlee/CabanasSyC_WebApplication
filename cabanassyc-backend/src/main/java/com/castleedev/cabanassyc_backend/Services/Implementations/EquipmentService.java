package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IEquipmentDAL;
import com.castleedev.cabanassyc_backend.Models.Equipment;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IEquipmentService;

@Service
public class EquipmentService implements IEquipmentService {
    
    @Autowired
    private IEquipmentDAL equipmentDAL;

    @Override
    public List<Equipment> getAllEquipments() {
        try {
            return equipmentDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all equipments", e);
        }
    }

    @Override
    public Equipment getEquipmentById(Long id) {
        try {
            return equipmentDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting an equipment", e);
        }
    }

    @Override
    public Equipment addEquipment(Equipment equipment) {
        try {
            return equipmentDAL.save(equipment);
        } catch (Exception e) {
            throw new RuntimeException("Error adding an equipment", e);
        }
    }

    @Override
    public Equipment updateEquipment(Equipment equipment) {
        try {
            return equipmentDAL.save(equipment);
        } catch (Exception e) {
            throw new RuntimeException("Error updating an equipment", e);
        }
    }

    @Override
    public void deleteEquipment(Long id) {
        try {
            equipmentDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting an equipment", e);
        }
    }

}