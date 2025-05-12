package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.ICabinTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinTypeDTO;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinTypeService;

@Service
@Transactional
public class CabinTypeService implements ICabinTypeService {

    private final ICabinTypeDAL cabinTypeDAL;

    public CabinTypeService(ICabinTypeDAL cabinTypeDAL) {
        this.cabinTypeDAL = cabinTypeDAL;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cabinTypes", key = "'all'")
    public List<CabinTypeDTO> getAllCabinTypes() {
        List<CabinType> cabinTypes = cabinTypeDAL.findAllByStateTrue();
        if (cabinTypes.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No cabin types found"
            );
        }

        return cabinTypes.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cabinTypes", key = "#id") 
    public CabinTypeDTO getCabinTypeById(Long id) {
        CabinType cabinType = cabinTypeDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin type not found"
            ));

        return convertToDTO(cabinType);
    }

    @Override
    @CacheEvict(value = "cabinTypes", allEntries = true)
    public CabinTypeDTO addCabinType(CabinTypeDTO cabinTypeDTO) {
        CabinType cabinType = convertToEntity(cabinTypeDTO);
        cabinType.setState(true);
        CabinType savedCabinType = cabinTypeDAL.save(cabinType);

        return convertToDTO(savedCabinType);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "cabinTypes", key = "#cabinTypeDTO.id"),
        @CacheEvict(value = "cabinTypes", key = "'all'")
    })
    public CabinTypeDTO updateCabinType(CabinTypeDTO cabinTypeDTO) {
        CabinType existingCabinType = cabinTypeDAL.findByIdAndStateTrue(cabinTypeDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin type not found"
            ));
        existingCabinType.setName(cabinTypeDTO.getName());
        existingCabinType.setCapacity(cabinTypeDTO.getCapacity());
        existingCabinType.setPrice(cabinTypeDTO.getPrice());
        existingCabinType.setState(cabinTypeDTO.isState());
        existingCabinType = cabinTypeDAL.save(existingCabinType);

        return convertToDTO(existingCabinType);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "cabinTypes", key = "#id"),
        @CacheEvict(value = "cabinTypes", key = "'all'")
    })
    public void deleteCabinType(Long id) {
        if (cabinTypeDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin type not found"
            );
        }
        int rowsAffected = cabinTypeDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete cabin type"
            );
        }
    }

    private CabinTypeDTO convertToDTO(CabinType cabinType) {
        if (cabinType == null) return null;
        
        return new CabinTypeDTO(
            cabinType.getId(),
            cabinType.getName(),
            cabinType.getCapacity(),
            cabinType.getPrice(),
            cabinType.isState()
        );
    }

    private CabinType convertToEntity(CabinTypeDTO cabinTypeDTO) {
        if (cabinTypeDTO == null) return null;
        
        return new CabinType(
            cabinTypeDTO.getId(),
            cabinTypeDTO.getName(),
            cabinTypeDTO.getCapacity(),
            cabinTypeDTO.getPrice(),
            cabinTypeDTO.isState()
        );
    }
}