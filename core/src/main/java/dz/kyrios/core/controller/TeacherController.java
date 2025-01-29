package dz.kyrios.core.controller;

import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanRequest;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanResponse;
import dz.kyrios.core.dto.teacher.TeacherRequest;
import dz.kyrios.core.dto.teacher.TeacherResponse;
import dz.kyrios.core.dto.teacher.TeacherResponseWithWorkingDay;
import dz.kyrios.core.dto.teacher.TeacherWorkingDayRequest;
import dz.kyrios.core.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @PreAuthorize("@authz.hasCustomAuthority('TEACHER_CREATE')")
    public ResponseEntity<Object> create(@RequestBody TeacherRequest request) {
        TeacherResponse response = teacherService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/working-day")
    @PreAuthorize("@authz.hasCustomAuthority('TEACHER_WORKING_DAY_ADD')")
    public ResponseEntity<Object> addWorkingDayToTeacher(@RequestBody TeacherWorkingDayRequest request) {
        TeacherResponseWithWorkingDay response = teacherService.addWorkingDayToTeacher(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/working-day/{working-day-id}")
    @PreAuthorize("@authz.hasCustomAuthority('TEACHER_WORKING_DAY_REMOVE')")
    public ResponseEntity<Object> removeWorkingDayFromTeacher(@PathVariable("working-day-id") Long workingDayId) {
        TeacherResponseWithWorkingDay response = teacherService.removeWorkingDayFromTeacher(workingDayId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
