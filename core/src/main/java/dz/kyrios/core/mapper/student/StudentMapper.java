package dz.kyrios.core.mapper.student;

import dz.kyrios.core.dto.student.StudentRequest;
import dz.kyrios.core.dto.student.StudentResponse;
import dz.kyrios.core.entity.Student;

public interface StudentMapper {

    Student requestToEntity(StudentRequest request);

    StudentResponse entityToResponse(Student entity);
}
