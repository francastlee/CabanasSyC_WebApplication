package com.castleedev.cabanassyc_backend.workingDays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.castleedev.cabanassyc_backend.DAL.IUserDAL;
import com.castleedev.cabanassyc_backend.DAL.IWorkingDayDAL;
import com.castleedev.cabanassyc_backend.DTO.WorkingDayDTO;
import com.castleedev.cabanassyc_backend.Models.UserModel;
import com.castleedev.cabanassyc_backend.Models.WorkingDay;
import com.castleedev.cabanassyc_backend.Services.Implementations.WorkingDayService;

@ExtendWith(MockitoExtension.class)
class WorkingDayServiceTest {

    @Mock
    private IWorkingDayDAL workingDayDAL;

    @Mock
    private IUserDAL userDAL;

    @InjectMocks
    private WorkingDayService workingDayService;

    private WorkingDay testWorkingDay;
    private WorkingDayDTO testWorkingDayDTO;
    private UserModel testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserModel(
            1L, 
            "ex", 
            "ex", 
            "test@example.com", 
            "password", 
            1500, 
            true
        );
        
        testWorkingDay = new WorkingDay(
            1L, 
            testUser, 
            LocalDate.now(), 
            Time.valueOf("09:00:00"), 
            Time.valueOf("17:00:00"), 
            true
        );
        
        testWorkingDayDTO = new WorkingDayDTO(
            1L, 
            1L, 
            LocalDate.now(), 
            Time.valueOf("09:00:00"), 
            Time.valueOf("17:00:00"), 
            true
        );
    }

    @Test
    void getAllWorkingDays_WhenWorkingDaysExist_ReturnsWorkingDayList() {
        List<WorkingDay> mockWorkingDays = List.of(testWorkingDay);
        when(workingDayDAL.findAllByStateTrue()).thenReturn(mockWorkingDays);

        List<WorkingDayDTO> result = workingDayService.getAllWorkingDays();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        verify(workingDayDAL).findAllByStateTrue();
    }

    @Test
    void getAllWorkingDays_WhenNoWorkingDays_ThrowsNotFoundException() {
        when(workingDayDAL.findAllByStateTrue()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> workingDayService.getAllWorkingDays())
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("No working days found");
    }

    @Test
    void getWorkingDayById_WhenNotExists_ThrowsNotFoundException() {
        when(workingDayDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workingDayService.getWorkingDayById(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Working day not found");
    }

    @Test
    void addWorkingDay_WithNonExistingUser_ThrowsNotFoundException() {
        when(userDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workingDayService.addWorkingDay(testWorkingDayDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("User not found");
    }

    @Test
    void updateWorkingDay_WithNonExistingId_ThrowsNotFoundException() {
        WorkingDayDTO updateDTO = new WorkingDayDTO(
            99L, 
            1L, 
            LocalDate.of(2023, 1, 2),
            Time.valueOf("10:00:00"), 
            Time.valueOf("18:00:00"), 
            true
        );
        when(workingDayDAL.findByIdAndStateTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workingDayService.updateWorkingDay(updateDTO))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Working day not found");
    }

    @Test
    void deleteWorkingDay_WithExistingId_DeletesSuccessfully() {
        when(workingDayDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testWorkingDay));
        when(workingDayDAL.softDeleteById(1L)).thenReturn(1);

        workingDayService.deleteWorkingDay(1L);

        verify(workingDayDAL).softDeleteById(1L);
    }

    @Test
    void deleteWorkingDay_WithNonExistingId_ThrowsNotFoundException() {
        when(workingDayDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workingDayService.deleteWorkingDay(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Working day not found");
    }

    @Test
    void deleteWorkingDay_WhenDeleteFails_ThrowsInternalError() {
        when(workingDayDAL.findByIdAndStateTrue(1L)).thenReturn(Optional.of(testWorkingDay));
        when(workingDayDAL.softDeleteById(1L)).thenReturn(0);

        assertThatThrownBy(() -> workingDayService.deleteWorkingDay(1L))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Failed to delete working day");
    }
}