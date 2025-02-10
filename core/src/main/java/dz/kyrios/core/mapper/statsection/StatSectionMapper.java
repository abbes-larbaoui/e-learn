package dz.kyrios.core.mapper.statsection;

import dz.kyrios.core.dto.statsection.StatSectionResponse;
import dz.kyrios.core.entity.StatSection;

public interface StatSectionMapper {

    StatSectionResponse entityToResponse(StatSection entity);
}
