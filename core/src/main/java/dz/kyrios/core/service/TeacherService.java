package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.teacher.TeacherRequest;
import dz.kyrios.core.dto.teacher.TeacherResponse;
import dz.kyrios.core.entity.Profile;
import dz.kyrios.core.entity.Teacher;
import dz.kyrios.core.mapper.teacher.TeacherMapper;
import dz.kyrios.core.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final ProfileService profileService;

    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository,
                          ProfileService profileService,
                          TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.profileService = profileService;
        this.teacherMapper = teacherMapper;
    }

    public Teacher getTeacherFromCurrentProfile() {
        Profile profile = profileService.getCurrentProfile();
        return teacherRepository.getTeacherByProfile(profile)
                .orElseThrow(() -> new NotFoundException(profile.getId(),  "Teacher not found with Profile id: "));
    }

    public TeacherResponse create(TeacherRequest request) {
        Profile profile = profileService.getCurrentProfile();
        Teacher entity = teacherMapper.requestToEntity(request);
        entity.setProfile(profile);

        return teacherMapper.entityToResponse(teacherRepository.save(entity));
    }
}
