package dz.kyrios.core.dto.teacher;

import java.time.DayOfWeek;
import java.util.Set;

public record TeacherWorkingDayRequest(Long id,
                                       DayOfWeek dayOfWeek,
                                       Set<Long> sessionStartingTimeIds) {
}
