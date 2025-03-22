package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.ICabinDAL;
import com.castleedev.cabanassyc_backend.DAL.ICabinEquipmentDAL;
import com.castleedev.cabanassyc_backend.DAL.IEquipmentDAL;
import com.castleedev.cabanassyc_backend.DTO.CabinEquipmentDTO;
import com.castleedev.cabanassyc_backend.Models.Cabin;
import com.castleedev.cabanassyc_backend.Models.CabinEquipment;
import com.castleedev.cabanassyc_backend.Models.Equipment;
import com.castleedev.cabanassyc_backend.Services.Interfaces.ICabinEquipmentService;

import java.util.ArrayList;

@Service
public class CabinEquipmentService implements ICabinEquipmentService {

    @Autowired
    private ICabinEquipmentDAL cabinEquipmentDAL;

    @Autowired
    private ICabinDAL cabinDAL;

    @Autowired
    private IEquipmentDAL equipmentDAL;
    

    CabinEquipmentDTO convertir (CabinEquipment cabinEquipment) {
        CabinEquipmentDTO cabinEquipmentDTO = new CabinEquipmentDTO(
            cabinEquipment.getId(),
            cabinEquipment.getCabin().getId(),
            cabinEquipment.getEquipment().getId(),
            cabinEquipment.isState()
        );
        return cabinEquipmentDTO;
    }

    CabinEquipment convertir (CabinEquipmentDTO cabinEquipmentDTO) {
        Equipment equipment = equipmentDAL.findByIdAndStateTrue(cabinEquipmentDTO.getEquipmentId());
        if (equipment == null) {
            throw new RuntimeException("Equipment not found");
        }
        Cabin cabin = cabinDAL.findByIdAndStateTrue(cabinEquipmentDTO.getCabinId());
        if (cabin == null) {
            throw new RuntimeException("Cabin not found");
        }
        CabinEquipment cabinEquipment = new CabinEquipment(
            cabinEquipmentDTO.getId(),
            cabin,
            equipment,
            cabinEquipmentDTO.isState()
        );
        return cabinEquipment;
    }

    @Override
    public List<CabinEquipmentDTO> getAllCabinEquipments() {
        try {
            List<CabinEquipment> cabinEquipments = cabinEquipmentDAL.findAllByStateTrue();
            List<CabinEquipmentDTO> cabinEquipmentsDTO = new ArrayList<CabinEquipmentDTO>();
            for (CabinEquipment cabinEquipment : cabinEquipments) {
                cabinEquipmentsDTO.add(convertir(cabinEquipment));
            }
            return cabinEquipmentsDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all cabin equipments: " + e.getMessage());
        }
    }

    @Override
    public CabinEquipmentDTO getCabinEquipmentById(Long id) {
        try {
            CabinEquipment cabinEquipment = cabinEquipmentDAL.findByIdAndStateTrue(id);
            return convertir(cabinEquipment);
        } catch (Exception e) {
            throw new RuntimeException("Error getting cabin equipment by id: "+ e.getMessage());
        }
    }

    @Override
    public CabinEquipmentDTO addCabinEquipment(CabinEquipmentDTO cabinEquipmentDTO) {
        try {
            CabinEquipment newCabinEquipment = convertir(cabinEquipmentDTO);
            return convertir(cabinEquipmentDAL.save(newCabinEquipment)); 
        } catch (Exception e) {
            throw new RuntimeException("Error adding cabin equipment: " + e.getMessage());
        }
    }

    @Override
    public CabinEquipmentDTO updateCabinEquipment(CabinEquipmentDTO cabinEquipmentDTO) {
        try {
            CabinEquipment cabinEquipment = convertir(cabinEquipmentDTO);
            return convertir(cabinEquipmentDAL.save(cabinEquipment));
        } catch (Exception e) {
            throw new RuntimeException("Error updating cabin equipment: " + e.getMessage());
        }
    }

    @Override
    public void deleteCabinEquipment(Long id) {
        try {
            cabinEquipmentDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting cabin equipment: " + e.getMessage());
        }
    }
    
}
