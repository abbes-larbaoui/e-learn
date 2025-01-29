package dz.kyrios.core.mapper.student;

import dz.kyrios.core.dto.student.StudentRequest;
import dz.kyrios.core.dto.student.StudentResponse;
import dz.kyrios.core.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapperImp implements StudentMapper {
    @Override
    public Student requestToEntity(StudentRequest request) {
        Student student = new Student();
        student.setId(request.id());
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setStatus(request.status());
        return student;
    }

    @Override
    public StudentResponse entityToResponse(Student entity) {
        return new StudentResponse(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getStatus());
    }
}
