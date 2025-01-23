package dz.kyrios.core.entity;

import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;

@Data
public class TeacherWorkingDay {

    private Long id;

    private Teacher teacher;

    private DayOfWeek dayOfWeek;

    private List<String> availableHours;
}
