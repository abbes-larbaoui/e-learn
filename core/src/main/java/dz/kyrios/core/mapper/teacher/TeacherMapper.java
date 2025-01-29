package dz.kyrios.core.mapper.teacher;

import dz.kyrios.core.dto.teacher.TeacherRequest;
import dz.kyrios.core.dto.teacher.TeacherResponse;
import dz.kyrios.core.entity.Teacher;

public interface TeacherMapper {

    Teacher requestToEntity(TeacherRequest request);

    TeacherResponse entityToResponse(Teacher teacher);
}
