package dz.kyrios.core.dto.teacher;

import dz.kyrios.core.entity.AvailableSlot;

import java.time.DayOfWeek;
import java.util.List;

public record TeacherWorkingDayResponse(Long id,
                                        DayOfWeek dayOfWeek,
                                        List<AvailableSlot> availableSlots) {
}
