package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinService;

@Service
public class CabinService implements ICabinService {
    
    @Autowired
    private ICabinDAL cabinDAL;

    @Override
    public List<Cabin> getAllCabins() {
        try {
            return cabinDAL.findAllByStateTrue();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabins", e);
        }
    }

    @Override
    public Cabin getCabinById(Long id) {
        try {
            return cabinDAL.findByIdAndStateTrue(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a cabin", e);
        }
    }

    @Override
    public Cabin addCabin(Cabin cabin) {
        try {
            return cabinDAL.save(cabin);
        } catch (Exception e) {
            throw new RuntimeException("Error adding a cabin", e);
        }
    }

    @Override
    public Cabin updateCabin(Cabin cabin) {
        try {
            return cabinDAL.save(cabin);
        } catch (Exception e) {
            throw new RuntimeException("Error updating a cabin", e);
        }
    }

    @Override
    public void deleteCabin(Long id) {
        try {
            cabinDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting a cabin", e);
        }
    }
    
}
