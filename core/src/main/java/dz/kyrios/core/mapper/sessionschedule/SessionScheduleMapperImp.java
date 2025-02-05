package dz.kyrios.core.mapper.sessionschedule;

import dz.kyrios.core.dto.sessionschedule.SessionScheduleResponse;
import dz.kyrios.core.dto.student.StudentResponse;
import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.dto.teacher.TeacherResponse;
import dz.kyrios.core.entity.SessionSchedule;
import dz.kyrios.core.mapper.student.StudentMapper;
import dz.kyrios.core.mapper.subject.SubjectMapper;
import dz.kyrios.core.mapper.teacher.TeacherMapper;
import org.springframework.stereotype.Component;

@Component
public class SessionScheduleMapperImp implements SessionScheduleMapper {

    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;
    private final SubjectMapper subjectMapper;

    public SessionScheduleMapperImp(TeacherMapper teacherMapper,
                                    StudentMapper studentMapper,
                                    SubjectMapper subjectMapper) {
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public SessionScheduleResponse entityToResponse(SessionSchedule entity) {

        TeacherResponse teacher = teacherMapper.entityToResponse(entity.getStudentSubscription().getSubscriptionPlan().getTeacher());
        StudentResponse student = studentMapper.entityToResponse(entity.getStudentSubscription().getStudent());
        SubjectResponse subject = subjectMapper.entityToResponse(entity.getStudentSubscription().getSubscriptionPlan().getSubject());

        return new SessionScheduleResponse(
                entity.getId(),
                teacher,
                student,
                subject,
                entity.getSessionDate(),
                entity.getStartingTime(),
                entity.getStatus()
        );
    }
}
