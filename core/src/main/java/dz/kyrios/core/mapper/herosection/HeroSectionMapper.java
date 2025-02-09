package dz.kyrios.core.mapper.herosection;

import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.entity.HeroSection;

public interface HeroSectionMapper {

    HeroSectionResponse entityToResponse(HeroSection entity);
}
