package dz.kyrios.core.dto.teacher;

import dz.kyrios.core.statics.GeneralStatus;

import java.util.List;

public record TeacherResponseWithWorkingDay(Long id,
                                            String firstName,
                                            String lastName,
                                            String bio,
                                            List<TeacherWorkingDayResponse> workingDays,
                                            GeneralStatus status) {
}
