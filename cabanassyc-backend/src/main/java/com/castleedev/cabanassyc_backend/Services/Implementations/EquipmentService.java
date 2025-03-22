package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IEquipmentDAL;
import com.castleedev.cabanassyc_backend.DTO.EquipmentDTO;
import com.castleedev.cabanassyc_backend.Models.Equipment;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IEquipmentService;
import java.util.ArrayList;

@Service
public class EquipmentService implements IEquipmentService {
    
    @Autowired
    private IEquipmentDAL equipmentDAL;

    EquipmentDTO convertir (Equipment equipment) {
        return new EquipmentDTO(
            equipment.getId(), 
            equipment.getName(), 
            equipment.isState()
        );
    }

    Equipment convertir (EquipmentDTO equipmentDTO) {
        return new Equipment(
            equipmentDTO.getId(), 
            equipmentDTO.getName(), 
            equipmentDTO.isState()
        );
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        try {
            List<Equipment> equipmentList = equipmentDAL.findAllByStateTrue();
            List<EquipmentDTO> equipmentDTOList = new ArrayList<EquipmentDTO>();
            for (Equipment equipment : equipmentList) {
                equipmentDTOList.add(convertir(equipment));
            }
            return equipmentDTOList;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all equipments", e);
        }
    }

    @Override
    public EquipmentDTO getEquipmentById(Long id) {
        try {
            Equipment equipment = equipmentDAL.findByIdAndStateTrue(id);
            return convertir(equipment);
        } catch (Exception e) {
            throw new RuntimeException("Error getting an equipment", e);
        }
    }

    @Override
    public EquipmentDTO addEquipment(EquipmentDTO equipmentDTO) {
        try {
            Equipment equipment = convertir(equipmentDTO);
            return convertir(equipmentDAL.save(equipment));
        } catch (Exception e) {
            throw new RuntimeException("Error adding an equipment", e);
        }
    }

    @Override
    public EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO) {
        try {
            Equipment equipment = convertir(equipmentDTO);
            return convertir(equipmentDAL.save(equipment));
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