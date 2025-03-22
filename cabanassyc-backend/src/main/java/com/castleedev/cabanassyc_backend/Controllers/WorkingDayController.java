package com.castleedev.cabanassyc_backend.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castleedev.cabanassyc_backend.DTO.WorkingDayDTO;
import com.castleedev.cabanassyc_backend.Services.Interfaces.IWorkingDayService;

@RestController
@RequestMapping("/workingdays")
public class WorkingDayController {
    
    private final IWorkingDayService workingDayService;

    public WorkingDayController(IWorkingDayService workingDayService) {
        this.workingDayService = workingDayService;
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkingDays() {
        try {
            List<WorkingDayDTO> workingDays = workingDayService.getAllWorkingDays();
            if (workingDays.isEmpty()) {
                ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "No workingDays found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<List<WorkingDayDTO>> apiResponse = new ApiResponse<>(true, "WorkingDays found successfully", workingDays);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Error during the getAll process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkingDayById(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            WorkingDayDTO workingDay = workingDayService.getWorkingDayById(id);
            if (workingDay == null) {
                ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "WorkingDay not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(true, "WorkingDay found successfully", workingDay);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Error during the getById process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addWorkingDay(@RequestBody WorkingDayDTO workingDay) {
        try {
            WorkingDayDTO newWorkingDay = new WorkingDayDTO();
            newWorkingDay.setUserId(workingDay.getUserId());
            newWorkingDay.setDate(workingDay.getDate());
            newWorkingDay.setCheckInTime(workingDay.getCheckInTime());
            newWorkingDay.setCheckOutTime(workingDay.getCheckOutTime());
            newWorkingDay.setState(true);
            workingDayService.addWorkingDay(newWorkingDay);
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(true, "WorkingDay added successfully", newWorkingDay);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Error during the add process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkingDay(@PathVariable("id") Long id, @RequestBody WorkingDayDTO workingDay) {
        try {
            if (id <= 0) {
                ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            workingDay.setId(id);
            WorkingDayDTO updatedWorkingDay = workingDayService.updateWorkingDay(workingDay);
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(true, "WorkingDay updated successfully", updatedWorkingDay);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Error during the update process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteWorkingDay(@PathVariable("id") Long id) {
        try {
            if (id <= 0) {
                ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Invalid ID");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }
            WorkingDayDTO workingDay = workingDayService.getWorkingDayById(id);
            if (workingDay == null) {
                ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "WorkingDay not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
            workingDayService.deleteWorkingDay(id);
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(true, "WorkingDay deleted successfully", workingDay);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<WorkingDayDTO> apiResponse = new ApiResponse<>(false, "Error during the delete process: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(apiResponse);
        }
    }
    
}