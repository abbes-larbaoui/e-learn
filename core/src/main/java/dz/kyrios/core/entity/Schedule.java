package dz.kyrios.core.entity;

import dz.kyrios.core.statics.ScheduleStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Schedule {

    private Long id;

    private Teacher teacher;

    private Student student;

    private String sessionUrl;

    private LocalDateTime time;

    private ScheduleStatus status;
}
