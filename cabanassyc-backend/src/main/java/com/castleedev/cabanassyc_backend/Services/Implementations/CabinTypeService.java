package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.ICabinTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinTypeDTO;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinTypeService;

@Service
public class CabinTypeService implements ICabinTypeService {
    
    @Autowired
    private ICabinTypeDAL cabinTypeDAL;

    CabinTypeDTO convertir (CabinType cabinType) {
        return new CabinTypeDTO(
            cabinType.getId(),
            cabinType.getName(),
            cabinType.getCapacity(),
            cabinType.getPrice(),
            cabinType.isState()
        );
    }

    CabinType convertir (CabinTypeDTO cabinTypeDTO) {
        return new CabinType(
            cabinTypeDTO.getId(),
            cabinTypeDTO.getName(),
            cabinTypeDTO.getCapacity(),
            cabinTypeDTO.getPrice(),
            cabinTypeDTO.isState()
        );
    }

    @Override
    public List<CabinTypeDTO> getAllCabinTypes() {
        try {
            List<CabinType> cabinTypes = cabinTypeDAL.findAllByStateTrue();
            List<CabinTypeDTO> cabinTypesDTO = new ArrayList<CabinTypeDTO>();
            for (CabinType cabinType : cabinTypes) {
                cabinTypesDTO.add(convertir(cabinType));
            }
            return cabinTypesDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabin types", e);
        }
    }

    @Override
    public CabinTypeDTO getCabinTypeById(Long id) {
        try {
            CabinType cabinType = cabinTypeDAL.findByIdAndStateTrue(id);
            return convertir(cabinType);
        } catch (Exception e) {
            throw new RuntimeException("Error getting a cabin type", e);
        }
    }

    @Override
    public CabinTypeDTO addCabinType(CabinTypeDTO cabinType) {
        try {
            CabinType cabinTypeModel = convertir(cabinType);
            return convertir(cabinTypeDAL.save(cabinTypeModel));
        } catch (Exception e) {
            throw new RuntimeException("Error adding a cabin type", e);
        }
    }

    @Override
    public CabinTypeDTO updateCabinType(CabinTypeDTO cabinType) {
        try {
            CabinType cabinTypeModel = convertir(cabinType);
            return convertir(cabinTypeDAL.save(cabinTypeModel));
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