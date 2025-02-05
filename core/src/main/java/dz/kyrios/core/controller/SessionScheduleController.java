package dz.kyrios.core.controller;

import dz.kyrios.core.dto.sessionschedule.SessionScheduleResponse;
import dz.kyrios.core.entity.SessionSchedule;
import dz.kyrios.core.service.SessionScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
public class SessionScheduleController {

    private final SessionScheduleService sessionScheduleService;

    public SessionScheduleController(SessionScheduleService sessionScheduleService) {
        this.sessionScheduleService = sessionScheduleService;
    }

    @GetMapping("/teacher/{year}/{month}")
    public ResponseEntity<List<SessionScheduleResponse>> getTeacherSchedule(
            @PathVariable int year,
            @PathVariable int month) {

        List<SessionScheduleResponse> schedules = sessionScheduleService.getTeacherScheduleForMonth(year, month);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/student/{year}/{month}")
    public ResponseEntity<List<SessionScheduleResponse>> getStudentSchedule(
            @PathVariable int year,
            @PathVariable int month) {

        List<SessionScheduleResponse> schedules = sessionScheduleService.getStudentScheduleForMonth(year, month);
        return ResponseEntity.ok(schedules);
    }
}
