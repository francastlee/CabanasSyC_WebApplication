package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.ICabinTypeDAL;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinTypeService;

@Service
public class CabinTypeService implements ICabinTypeService {
    
    @Autowired
    private ICabinTypeDAL cabinTypeDAL;

    @Override
    public List<CabinType> getAllCabinTypes() {
        try {
            return cabinTypeDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabin types", e);
        }
    }

    @Override
    public CabinType getCabinTypeById(Long id) {
        try {
            return cabinTypeDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a cabin type", e);
        }
    }

    @Override
    public CabinType addCabinType(CabinType cabinType) {
        try {
            return cabinTypeDAL.save(cabinType);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a cabin type", e);
        }
    }

    @Override
    public CabinType updateCabinType(CabinType cabinType) {
        try {
            return cabinTypeDAL.save(cabinType);
        } catch (Exception e) {
            throw new RuntimeException("Error updating a cabin type", e);
        }
    }

    @Override
    public void deleteCabinType(Long id) {
        try {
            cabinTypeDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a cabin type", e);
        }
    }
    
}