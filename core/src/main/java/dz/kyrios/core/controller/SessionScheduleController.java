package dz.kyrios.core.controller;

import dz.kyrios.core.dto.sessionschedule.SessionScheduleResponse;
import dz.kyrios.core.service.SessionScheduleService;
import dz.kyrios.core.statics.SessionScheduleStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/complete/{session-schedule-id}")
    @PreAuthorize("@authz.hasCustomAuthority('SESSION_SCHEDULE_COMPLETE')")
    public ResponseEntity<SessionScheduleResponse> completeSessionSchedule(
            @PathVariable("session-schedule-id") Long sessionScheduleId) {

        SessionScheduleResponse response = sessionScheduleService
                .changeStatusSessionSchedule(sessionScheduleId, SessionScheduleStatus.COMPLETED);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cancel/{session-schedule-id}")
    @PreAuthorize("@authz.hasCustomAuthority('SESSION_SCHEDULE_CANCEL')")
    public ResponseEntity<SessionScheduleResponse> cancelSessionSchedule(
            @PathVariable("session-schedule-id") Long sessionScheduleId) {

        SessionScheduleResponse response = sessionScheduleService
                .changeStatusSessionSchedule(sessionScheduleId, SessionScheduleStatus.CANCELED);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/miss/{session-schedule-id}")
    @PreAuthorize("@authz.hasCustomAuthority('SESSION_SCHEDULE_MISS')")
    public ResponseEntity<SessionScheduleResponse> missSessionSchedule(
            @PathVariable("session-schedule-id") Long sessionScheduleId) {

        SessionScheduleResponse response = sessionScheduleService
                .changeStatusSessionSchedule(sessionScheduleId, SessionScheduleStatus.MISSED);
        return ResponseEntity.ok(response);
    }
}
