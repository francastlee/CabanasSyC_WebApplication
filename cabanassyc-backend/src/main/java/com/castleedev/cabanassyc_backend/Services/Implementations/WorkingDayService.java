package com.castleedev.cabanassyc_backend.Services.Implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DAL.IWorkingDayDAL;
import com.castleedev.cabanassyc_backend.DTO.WorkingDayDTO;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Models.WorkingDay;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IWorkingDayService;

@Service
@Transactional
public class WorkingDayService implements IWorkingDayService {
    
    private final IWorkingDayDAL workingDayDAL;
    private final IUserDAL userDAL;

    public WorkingDayService(
            IWorkingDayDAL workingDayDAL,
            IUserDAL userDAL) {
        this.workingDayDAL = workingDayDAL;
        this.userDAL = userDAL;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkingDayDTO> getAllWorkingDays() {
        List<WorkingDay> workingDays = workingDayDAL.findAllByStateTrue();
        if (workingDays.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "No working days found"
            );
        }
        
        return workingDays.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkingDayDTO getWorkingDayById(Long id) {
        WorkingDay workingDay = workingDayDAL.findByIdAndStateTrue(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Working day not found"
            ));
        
        return convertToDTO(workingDay);
    }

    @Override
    public WorkingDayDTO addWorkingDay(WorkingDayDTO workingDayDTO) {
        validateWorkingDayTimes(workingDayDTO);
        WorkingDay workingDay = convertToEntity(workingDayDTO);
        workingDay.setState(true);
        WorkingDay savedWorkingDay = workingDayDAL.save(workingDay);
        
        return convertToDTO(savedWorkingDay);
    }

    @Override
    public WorkingDayDTO updateWorkingDay(WorkingDayDTO workingDayDTO) {
        validateWorkingDayTimes(workingDayDTO);
        WorkingDay existingWorkingDay = workingDayDAL.findByIdAndStateTrue(workingDayDTO.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Working day not found"
            ));
        existingWorkingDay.setCheckInTime(workingDayDTO.getCheckInTime());
        existingWorkingDay.setCheckOutTime(workingDayDTO.getCheckOutTime());
        existingWorkingDay.setDate(workingDayDTO.getDate());
        existingWorkingDay.setUser(userDAL.findByIdAndStateTrue(workingDayDTO.getUserId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "User not found"
            )));
        existingWorkingDay.setState(workingDayDTO.isState());
        WorkingDay updatedWorkingDay = workingDayDAL.save(existingWorkingDay);

        return convertToDTO(updatedWorkingDay);
    }

    @Override
    public void deleteWorkingDay(Long id) {
        if (workingDayDAL.findByIdAndStateTrue(id).isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Working day not found"
            );
        }
        int rowsAffected = workingDayDAL.softDeleteById(id);
        if (rowsAffected <= 0) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Failed to delete working day" 
            );
        }
    }

    private void validateWorkingDayTimes(WorkingDayDTO workingDayDTO) {
        if (workingDayDTO.getCheckOutTime() != null && 
            workingDayDTO.getCheckInTime() != null &&
            workingDayDTO.getCheckOutTime().before(workingDayDTO.getCheckInTime())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Check-out time cannot be before check-in time"
            );
        }
    }

    private WorkingDayDTO convertToDTO(WorkingDay workingDay) {
        if (workingDay == null) return null;
        
        return new WorkingDayDTO(
            workingDay.getId(),
            workingDay.getUser().getId(),
            workingDay.getDate(),
            workingDay.getCheckInTime(),
            workingDay.getCheckOutTime(),
            workingDay.isState()
        );
    }

    private WorkingDay convertToEntity(WorkingDayDTO workingDayDTO) {
        if (workingDayDTO == null) return null;

        UserModel user = userDAL.findByIdAndStateTrue(workingDayDTO.getUserId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));

        return new WorkingDay(
            workingDayDTO.getId(),
            user,
            workingDayDTO.getDate(),
            workingDayDTO.getCheckInTime(),
            workingDayDTO.getCheckOutTime(),
            workingDayDTO.isState()
        );
    }
}