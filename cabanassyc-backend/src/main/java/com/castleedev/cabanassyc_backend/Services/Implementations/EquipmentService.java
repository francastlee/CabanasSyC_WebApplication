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

import com.castleedev.cabanassyc_backend.DAL.IEquipmentDAL;
import com.castleedev.cabanassyc_backend.DTO.EquipmentDTO;
import com.castleedev.cabanassyc_backend.Models.Equipment;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IEquipmentService;

@Service
@Transactional
public class EquipmentService implements IEquipmentService {

    private final IEquipmentDAL equipmentDAL;

    public EquipmentService(IEquipmentDAL equipmentDAL) {
        this.equipmentDAL = equipmentDAL;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "equipments", key = "'all'")
    public List<EquipmentDTO> getAllEquipments() {
        List<Equipment> equipments = equipmentDAL.findAllByStateTrue();
        if (equipments.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No equipments found"
            );  
        }

        return equipments.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "equipments", key = "#id")
    public EquipmentDTO getEquipmentById(Long id) {
        Equipment equipment = equipmentDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Equipment not found"
            ));

        return convertToDTO(equipment);
    }

    @Override
    @CacheEvict(value = "equipments", allEntries = true)
    public EquipmentDTO addEquipment(EquipmentDTO equipmentDTO) {
        Equipment equipment = convertToEntity(equipmentDTO);
        equipment.setState(true);
        Equipment savedEquipment = equipmentDAL.save(equipment);

        return convertToDTO(savedEquipment);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "equipments", key = "#equipmentDTO.id"),
        @CacheEvict(value = "equipments", key = "'all'")
    })
    public EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO) {
        Equipment existingEquipment = equipmentDAL.findByIdAndStateTrue(equipmentDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Equipment not found"
            ));
        existingEquipment.setName(equipmentDTO.getName());
        existingEquipment.setState(equipmentDTO.isState());
        existingEquipment = equipmentDAL.save(existingEquipment);

        return convertToDTO(existingEquipment);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "equipments", key = "#id"),
        @CacheEvict(value = "equipments", key = "'all'")
    })
    public void deleteEquipment(Long id) {
        if (equipmentDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Equipment not found"
            );
        }
        int rowsAffected = equipmentDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete equipment"
            );
        }
    }

    private EquipmentDTO convertToDTO(Equipment equipment) {
        if (equipment == null) return null;
        
        return new EquipmentDTO(
            equipment.getId(),
            equipment.getName(),
            equipment.isState()
        );
    }

    private Equipment convertToEntity(EquipmentDTO equipmentDTO) {
        if (equipmentDTO == null) return null;
        
        return new Equipment(
            equipmentDTO.getId(),
            equipmentDTO.getName(),
            equipmentDTO.isState()
        );
    }
}