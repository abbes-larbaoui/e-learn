package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.teacher.TeacherRequest;
import dz.kyrios.core.dto.teacher.TeacherResponse;
import dz.kyrios.core.dto.teacher.TeacherResponseWithWorkingDay;
import dz.kyrios.core.dto.teacher.TeacherWorkingDayRequest;
import dz.kyrios.core.entity.Profile;
import dz.kyrios.core.entity.Teacher;
import dz.kyrios.core.entity.TeacherWorkingDay;
import dz.kyrios.core.mapper.teacher.TeacherMapper;
import dz.kyrios.core.repository.TeacherRepository;
import dz.kyrios.core.repository.TeacherWorkingDayRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherWorkingDayRepository teacherWorkingDayRepository;

    private final ProfileService profileService;

    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository,
                          TeacherWorkingDayRepository teacherWorkingDayRepository,
                          ProfileService profileService,
                          TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherWorkingDayRepository = teacherWorkingDayRepository;
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

    @Transactional
    public TeacherResponseWithWorkingDay addWorkingDayToTeacher(TeacherWorkingDayRequest request) {
        TeacherWorkingDay teacherWorkingDay = teacherMapper.requestToEntity(request);
        Teacher teacher = getTeacherFromCurrentProfile();
        teacherWorkingDay.setTeacher(teacher);

        validateNewWorkingDay(teacher.getWorkingDays(), teacherWorkingDay);

        teacher.getWorkingDays().add(teacherWorkingDay);
        return teacherMapper.entityToResponseWithWorkingDay(teacherRepository.save(teacher));
    }

    @Transactional
    public TeacherResponseWithWorkingDay removeWorkingDayFromTeacher(Long workingDayId) {
        TeacherWorkingDay teacherWorkingDay = teacherWorkingDayRepository.findById(workingDayId)
                .orElseThrow(() -> new NotFoundException(workingDayId, "Teacher Working Day not found with id: "));
        Teacher teacher = getTeacherFromCurrentProfile();
        teacher.getWorkingDays().remove(teacherWorkingDay);
        teacherWorkingDayRepository.delete(teacherWorkingDay);
        return teacherMapper.entityToResponseWithWorkingDay(teacherRepository.save(teacher));
    }

    /**
     * Validates if a new working day can be added to a teacher.
     */
    private void validateNewWorkingDay(List<TeacherWorkingDay> existingDays, TeacherWorkingDay newWorkingDay) {
        for (TeacherWorkingDay existingDay : existingDays) {
            if (existingDay.getDayOfWeek().equals(newWorkingDay.getDayOfWeek())) {
                throw new IllegalArgumentException("Teacher already has a working day on " + newWorkingDay.getDayOfWeek());
            }
        }
    }

    public Long getTotalTeachers() {
        return teacherRepository.count();
    }

}
