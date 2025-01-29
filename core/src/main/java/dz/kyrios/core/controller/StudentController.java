package dz.kyrios.core.controller;

import dz.kyrios.core.dto.student.StudentRequest;
import dz.kyrios.core.dto.student.StudentResponse;
import dz.kyrios.core.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    @PreAuthorize("@authz.hasCustomAuthority('STUDENT_CREATE')")
    public ResponseEntity<Object> create(@RequestBody StudentRequest request) {
        StudentResponse response = studentService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
