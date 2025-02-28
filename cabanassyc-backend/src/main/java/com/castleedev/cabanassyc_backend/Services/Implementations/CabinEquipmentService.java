package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.ICabinEquipmentDAL;
import com.castleedev.cabanassyc_backend.Models.CabinEquipment;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinEquipmentService;

@Service
public class CabinEquipmentService implements ICabinEquipmentService {

    @Autowired
    private ICabinEquipmentDAL cabinEquipmentDAL;

    @Override
    public List<CabinEquipment> getAllCabinEquipments() {
        try {
            return cabinEquipmentDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabin equipments", e);
        }
    }

    @Override
    public CabinEquipment getCabinEquipmentById(Long id) {
        try {
            return cabinEquipmentDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting cabin equipment by id", e);
        }
    }

    @Override
    public CabinEquipment addCabinEquipment(CabinEquipment cabinEquipment) {
        try {
            return cabinEquipmentDAL.save(cabinEquipment);
        } catch (Exception e) {
            throw new RuntimeException("Error adding cabin equipment", e);
        }
    }

    @Override
    public CabinEquipment updateCabinEquipment(CabinEquipment cabinEquipment) {
        try {
            return cabinEquipmentDAL.save(cabinEquipment);
        } catch (Exception e) {
            throw new RuntimeException("Error updating cabin equipment", e);
        }
    }

    @Override
    public void deleteCabinEquipment(Long id) {
        try {
            cabinEquipmentDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting cabin equipment", e);
        }
    }
    
}
