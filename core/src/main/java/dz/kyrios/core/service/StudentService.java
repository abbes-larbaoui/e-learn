package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.student.StudentRequest;
import dz.kyrios.core.dto.student.StudentResponse;
import dz.kyrios.core.entity.Profile;
import dz.kyrios.core.entity.Student;
import dz.kyrios.core.mapper.student.StudentMapper;
import dz.kyrios.core.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final ProfileService profileService;

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentService(ProfileService profileService,
                          StudentRepository studentRepository,
                          StudentMapper studentMapper) {
        this.profileService = profileService;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Student getStudentFromCurrentProfile() {
        Profile profile = profileService.getCurrentProfile();
        return studentRepository.getStudentByProfile(profile)
                .orElseThrow(() -> new NotFoundException(profile.getId(),  "Student not found with Profile id: "));
    }

    public StudentResponse create(StudentRequest request) {
        Profile profile = profileService.getCurrentProfile();
        Student entity = studentMapper.requestToEntity(request);
        entity.setProfile(profile);

        return studentMapper.entityToResponse(studentRepository.save(entity));
    }
}
