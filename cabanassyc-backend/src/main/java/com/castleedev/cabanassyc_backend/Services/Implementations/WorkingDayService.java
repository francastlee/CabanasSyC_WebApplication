package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DAL.IWorkingDayDAL;
import com.castleedev.cabanassyc_backend.DTO.WorkingDayDTO;
import com.castleedev.cabanassyc_backend.Models.User;
import com.castleedev.cabanassyc_backend.Models.WorkingDay;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IWorkingDayService;
import java.util.ArrayList;

@Service
public class WorkingDayService implements IWorkingDayService {

    @Autowired
    private IWorkingDayDAL workingDayDAL;

    @Autowired
    private IUserDAL userDAL;

    WorkingDayDTO convertir (WorkingDay workingDay) {
        return new WorkingDayDTO(
            workingDay.getId(),
            workingDay.getUser().getId(),
            workingDay.getDate(),
            workingDay.getCheckInTime(),
            workingDay.getCheckOutTime(),
            workingDay.isState()
        );
    }

    WorkingDay convertir (WorkingDayDTO workingDayDTO) {
        User user = userDAL.findByIdAndStateTrue(workingDayDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new WorkingDay(
            workingDayDTO.getId(),
            user,
            workingDayDTO.getDate(),
            workingDayDTO.getCheckInTime(),
            workingDayDTO.getCheckOutTime(),
            workingDayDTO.isState()
        );
    }

    @Override
    public List<WorkingDayDTO> getAllWorkingDays() {
        try {
            List<WorkingDay> workingDays = workingDayDAL.findAllByStateTrue();
            List<WorkingDayDTO> workingDaysDTO = new ArrayList<WorkingDayDTO>();
            for (WorkingDay workingDay : workingDays) {
                workingDaysDTO.add(convertir(workingDay));
            }
            return workingDaysDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error getting all working days", e);
        }
    }

    @Override
    public WorkingDayDTO getWorkingDayById(Long id) {
        try {
            WorkingDay workingDay = workingDayDAL.findByIdAndStateTrue(id);
            return convertir(workingDay);   
        } catch (Exception e) {
            throw new RuntimeException("Error getting working day by id", e);
        }
    }

    @Override
    public WorkingDayDTO addWorkingDay(WorkingDayDTO workingDayDTO) {
        try {
            WorkingDay workingDayModel = convertir(workingDayDTO);
            return convertir(workingDayDAL.save(workingDayModel));
        } catch (Exception e) {
            throw new RuntimeException("Error adding working day", e);
        }
    }

    @Override
    public WorkingDayDTO updateWorkingDay(WorkingDayDTO workingDayDTO) {
        try {
            WorkingDay workingDayModel = convertir(workingDayDTO);
            return convertir(workingDayDAL.save(workingDayModel));
        } catch (Exception e) {
            throw new RuntimeException("Error updating working day", e);
        }
    }

    @Override
    public void deleteWorkingDay(Long id) {
        try {
            workingDayDAL.softDeleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting working day", e);
        }
    }
    
}