package dz.kyrios.core.dto.teacher;

import dz.kyrios.core.entity.SessionStartingTime;

import java.time.DayOfWeek;
import java.util.Set;

public record TeacherWorkingDayResponse(Long id,
                                        DayOfWeek dayOfWeek,
                                        Set<SessionStartingTime> sessionStartingTimes) {
}
