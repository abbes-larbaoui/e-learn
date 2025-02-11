package dz.kyrios.core.mapper.planssection;

import dz.kyrios.core.dto.planssection.PlansSectionResponse;
import dz.kyrios.core.entity.PlansSection;

public interface PlansSectionMapper {

    PlansSectionResponse entityToResponse(PlansSection entity);
}
