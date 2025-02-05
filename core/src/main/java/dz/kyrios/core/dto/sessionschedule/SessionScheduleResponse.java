package dz.kyrios.core.dto.sessionschedule;

import dz.kyrios.core.dto.student.StudentResponse;
import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.dto.teacher.TeacherResponse;
import dz.kyrios.core.statics.SessionScheduleStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record SessionScheduleResponse(Long id,
                                      TeacherResponse teacher,
                                      StudentResponse student,
                                      SubjectResponse subject,
                                      LocalDate sessionDate,
                                      LocalTime startingTime,
                                      SessionScheduleStatus status) {
}
