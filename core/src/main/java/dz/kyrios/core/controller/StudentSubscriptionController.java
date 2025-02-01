package dz.kyrios.core.controller;

import dz.kyrios.core.entity.SessionPlan;
import dz.kyrios.core.service.StudentSubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students/subscription")
public class StudentSubscriptionController {

    private final StudentSubscriptionService studentSubscriptionService;

    public StudentSubscriptionController(StudentSubscriptionService studentSubscriptionService) {
        this.studentSubscriptionService = studentSubscriptionService;
    }

    @PutMapping("/{subscription-plan-id}")
    @PreAuthorize("@authz.hasCustomAuthority('STUDENT_SUBSCRIBE')")
    public ResponseEntity<?> subscribe(@PathVariable("subscription-plan-id") Long subscribePlanId,
                                       @RequestBody List<SessionPlan> sessionPlans) {
        String paymentUri = studentSubscriptionService.subscribeStudentToPlan(subscribePlanId);
        return new ResponseEntity<>(paymentUri, HttpStatus.OK);
    }
}
