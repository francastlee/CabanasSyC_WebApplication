package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinEquipmentDAL;
import com.castleedev.cabanassyc_backend.DAL.IEquipmentDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinEquipmentDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinEquipment;
import com.castleedev.cabanassyc_backend.Models.Equipment;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinEquipmentService;

@Service
@Transactional
public class CabinEquipmentService implements ICabinEquipmentService {

    private final ICabinEquipmentDAL cabinEquipmentDAL;
    private final ICabinDAL cabinDAL;
    private final IEquipmentDAL equipmentDAL;

    public CabinEquipmentService(ICabinEquipmentDAL cabinEquipmentDAL,
                               ICabinDAL cabinDAL,
                               IEquipmentDAL equipmentDAL) {
        this.cabinEquipmentDAL = cabinEquipmentDAL;
        this.cabinDAL = cabinDAL;
        this.equipmentDAL = equipmentDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CabinEquipmentDTO> getAllCabinEquipments() {
        List<CabinEquipment> cabinEquipments = cabinEquipmentDAL.findAllByStateTrue();
        if (cabinEquipments.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No cabin equipment found"
            );
        }

        return cabinEquipments.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CabinEquipmentDTO getCabinEquipmentById(Long id) {
        CabinEquipment cabinEquipment = cabinEquipmentDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin equipment not found"
            ));
        
        return convertToDTO(cabinEquipment);
    }

    @Override
    public CabinEquipmentDTO addCabinEquipment(CabinEquipmentDTO cabinEquipmentDTO) {
        CabinEquipment cabinEquipment = convertToEntity(cabinEquipmentDTO);
        cabinEquipment.setState(true);
        CabinEquipment savedCabinEquipment = cabinEquipmentDAL.save(cabinEquipment);

        return convertToDTO(savedCabinEquipment);
    }

    @Override
public CabinEquipmentDTO updateCabinEquipment(CabinEquipmentDTO cabinEquipmentDTO) {
    CabinEquipment existingCabinEquipment = cabinEquipmentDAL.findByIdAndStateTrue(cabinEquipmentDTO.getId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Cabin equipment not found"
        ));
    
    Equipment equipment = equipmentDAL.findByIdAndStateTrue(cabinEquipmentDTO.getEquipmentId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Equipment not found"
        ));

    Cabin cabin = cabinDAL.findByIdAndStateTrue(cabinEquipmentDTO.getCabinId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Cabin not found"
        ));

    existingCabinEquipment.setCabin(cabin);
    existingCabinEquipment.setEquipment(equipment);
    existingCabinEquipment.setState(cabinEquipmentDTO.isState());
    CabinEquipment updatedCabinEquipment = cabinEquipmentDAL.save(existingCabinEquipment);
    
    return convertToDTO(updatedCabinEquipment);
}

    @Override
    public void deleteCabinEquipment(Long id) {
        if (cabinEquipmentDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin equipment not found"
            );
        }
        int rowsAffected = cabinEquipmentDAL.softDeleteById(id);
        if (rowsAffected == 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete cabin equipment"
            );
        }    
    }

    private CabinEquipmentDTO convertToDTO(CabinEquipment cabinEquipment) {
        if (cabinEquipment == null) return null;
        
        return new CabinEquipmentDTO(
            cabinEquipment.getId(),
            cabinEquipment.getCabin().getId(),
            cabinEquipment.getEquipment().getId(),
            cabinEquipment.isState()
        );
    }

    private CabinEquipment convertToEntity(CabinEquipmentDTO cabinEquipmentDTO) {
        if (cabinEquipmentDTO == null) return null;

        Cabin cabin = cabinDAL.findByIdAndStateTrue(cabinEquipmentDTO.getCabinId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Cabin not found"
            ));
        Equipment equipment = equipmentDAL.findByIdAndStateTrue(cabinEquipmentDTO.getEquipmentId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Equipment not found"
            ));

        return new CabinEquipment(
            cabinEquipmentDTO.getId(),
            cabin,
            equipment,
            cabinEquipmentDTO.isState()
        );
    }
}