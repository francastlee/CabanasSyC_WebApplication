package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinService;
import java.util.ArrayList;

@Service
public class CabinService implements ICabinService {
    
    @Autowired
    private ICabinDAL cabinDAL;

    @Autowired
    private ICabinTypeDAL cabinTypeDAL;


    CabinDTO convertir (Cabin cabin) {
        CabinDTO cabinDTO = new CabinDTO (
            cabin.getId(),
            cabin.getName(),
            cabin.getCabinType().getId(),
            cabin.isState()
        );
        return cabinDTO;
    }

    Cabin convertir (CabinDTO cabinDTO) {
        CabinType cabinType = cabinTypeDAL.findByIdAndStateTrue(cabinDTO.getCabinTypeId());
        if (cabinType == null) {
            throw new RuntimeException("Cabin type not found");
        }
        Cabin cabin = new Cabin (
            cabinDTO.getId(),
            cabinDTO.getName(),
            cabinType,
            cabinDTO.isState()
        );
        return cabin;
    }

    @Override
    public List<CabinDTO> getAllCabins() {
        try {
            List<Cabin> cabins = cabinDAL.findAllByStateTrue();
            List<CabinDTO> cabinsDTO = new ArrayList<CabinDTO>();
            for (Cabin cabin : cabins) {
                cabinsDTO.add(convertir(cabin));
            }
            return cabinsDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabins", e);
        }
    }

    @Override
    public CabinDTO getCabinById(Long id) {
        try {
            Cabin cabin = cabinDAL.findByIdAndStateTrue(id);
            return convertir(cabin);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a cabin", e);
        }
    }

    @Override
    public CabinDTO addCabin(CabinDTO cabinDTO) {
        try {
            Cabin newCabin = convertir(cabinDTO);
            return convertir(cabinDAL.save(newCabin));
        } catch (Exception e) {
            throw new RuntimeException("Error adding a cabin", e);
        }
    }

    @Override
    public CabinDTO updateCabin(CabinDTO cabinDTO) {
        try {
            Cabin cabin = convertir(cabinDTO);
            return convertir(cabinDAL.save(cabin));
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
