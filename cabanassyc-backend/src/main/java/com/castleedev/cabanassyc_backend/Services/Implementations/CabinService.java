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

import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinTypeDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinType;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinService;

@Service
@Transactional
public class CabinService implements ICabinService {

    private final ICabinDAL cabinDAL;
    private final ICabinTypeDAL cabinTypeDAL;

    public CabinService(ICabinDAL cabinDAL, ICabinTypeDAL cabinTypeDAL) {
        this.cabinDAL = cabinDAL;
        this.cabinTypeDAL = cabinTypeDAL;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cabins", key = "'all'") 
    public List<CabinDTO> getAllCabins() {
        List<Cabin> cabins = cabinDAL.findAllByStateTrue();
        if (cabins.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No cabins found"
            );
        }

        return cabins.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cabins", key = "#id")
    public CabinDTO getCabinById(Long id) {
        Cabin cabin = cabinDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin not found"
            ));

        return convertToDTO(cabin);
    }

    @Override
    @CacheEvict(value = "cabins", allEntries = true)
    public CabinDTO addCabin(CabinDTO cabinDTO) {
        Cabin cabin = convertToEntity(cabinDTO);
        cabin.setState(true);
        Cabin savedCabin = cabinDAL.save(cabin);
        return convertToDTO(savedCabin);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "cabins", key = "#cabinDTO.id"),
        @CacheEvict(value = "cabins", key = "'all'")
    })
    public CabinDTO updateCabin(CabinDTO cabinDTO) {
        Cabin existingCabin = cabinDAL.findByIdAndStateTrue(cabinDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin not found"
            ));
        CabinType cabinType = cabinTypeDAL.findByIdAndStateTrue(cabinDTO.getCabinTypeId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin type not found"
            ));
        existingCabin.setName(cabinDTO.getName());
        existingCabin.setCabinType(cabinType);
        existingCabin.setState(cabinDTO.isState());
        existingCabin = cabinDAL.save(existingCabin);

        return convertToDTO(existingCabin);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "cabins", key = "#id"), 
        @CacheEvict(value = "cabins", key = "'all'")
    })
    public void deleteCabin(Long id) {
        if (cabinDAL.findByIdAndStateTrue(id).isEmpty()) {

            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin not found"
            );
        }
        int rowsAffected = cabinDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed deleting cabin"
            );
        }
    }

    private CabinDTO convertToDTO(Cabin cabin) {
        if (cabin == null) return null;
        
        return new CabinDTO(
            cabin.getId(),
            cabin.getName(),
            cabin.getCabinType().getId(),
            cabin.isState()
        );
    }

    private Cabin convertToEntity(CabinDTO cabinDTO) {
        if (cabinDTO == null) return null;

        CabinType cabinType = cabinTypeDAL.findByIdAndStateTrue(cabinDTO.getCabinTypeId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cabin type not found"
            ));

        return new Cabin(
            cabinDTO.getId(),
            cabinDTO.getName(),
            cabinType,
            cabinDTO.isState()
        );
    }
}