package dz.kyrios.core.mapper.teacher;

import dz.kyrios.core.dto.teacher.*;
import dz.kyrios.core.entity.SessionStartingTime;
import dz.kyrios.core.entity.Teacher;
import dz.kyrios.core.entity.TeacherWorkingDay;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherMapperImp implements TeacherMapper {

    @Override
    public Teacher requestToEntity(TeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setId(request.id());
        teacher.setFirstName(request.firstName());
        teacher.setLastName(request.lastName());
        teacher.setBio(request.bio());
        teacher.setStatus(request.status());

        return teacher;
    }

    @Override
    public TeacherResponse entityToResponse(Teacher teacher) {
        return new TeacherResponse(teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getBio(),
                teacher.getStatus());
    }

    @Override
    public TeacherResponseWithWorkingDay entityToResponseWithWorkingDay(Teacher teacher) {
        List<TeacherWorkingDayResponse> workingDays = teacher
                .getWorkingDays()
                .stream()
                .map(this::entityToResponse).toList();

        return new TeacherResponseWithWorkingDay(teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getBio(),
                workingDays,
                teacher.getStatus());
    }

    @Override
    public TeacherWorkingDay requestToEntity(TeacherWorkingDayRequest request) {
        TeacherWorkingDay teacherWorkingDay = new TeacherWorkingDay();
        teacherWorkingDay.setId(request.id());
        teacherWorkingDay.setDayOfWeek(request.dayOfWeek());
        teacherWorkingDay.setSessionStartingTimes(request.sessionStartingTimeIds()
                .stream().map(SessionStartingTime::new).collect(Collectors.toSet()));

        return teacherWorkingDay;
    }

    @Override
    public TeacherWorkingDayResponse entityToResponse(TeacherWorkingDay teacherWorkingDay) {
        return new TeacherWorkingDayResponse(
                teacherWorkingDay.getId(),
                teacherWorkingDay.getDayOfWeek(),
                teacherWorkingDay.getSessionStartingTimes());
    }
}
