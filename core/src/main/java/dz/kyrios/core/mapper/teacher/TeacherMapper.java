package dz.kyrios.core.mapper.teacher;

import dz.kyrios.core.dto.teacher.*;
import dz.kyrios.core.entity.Teacher;
import dz.kyrios.core.entity.TeacherWorkingDay;

public interface TeacherMapper {

    Teacher requestToEntity(TeacherRequest request);

    TeacherResponse entityToResponse(Teacher teacher);

    TeacherResponseWithWorkingDay entityToResponseWithWorkingDay(Teacher teacher);

    TeacherWorkingDay requestToEntity(TeacherWorkingDayRequest request);

    TeacherWorkingDayResponse entityToResponse(TeacherWorkingDay teacherWorkingDay);
}
