package dz.kyrios.core.mapper.sessionschedule;

import dz.kyrios.core.dto.sessionschedule.SessionScheduleResponse;
import dz.kyrios.core.entity.SessionSchedule;

public interface SessionScheduleMapper {

    SessionScheduleResponse entityToResponse(SessionSchedule entity);
}
