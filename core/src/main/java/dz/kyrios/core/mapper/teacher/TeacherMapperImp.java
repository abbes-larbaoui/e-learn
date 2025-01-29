package dz.kyrios.core.mapper.teacher;

import dz.kyrios.core.dto.teacher.TeacherRequest;
import dz.kyrios.core.dto.teacher.TeacherResponse;
import dz.kyrios.core.entity.Teacher;
import org.springframework.stereotype.Component;

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
}
