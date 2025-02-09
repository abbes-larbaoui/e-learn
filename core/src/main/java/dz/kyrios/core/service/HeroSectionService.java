package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.entity.HeroSection;
import dz.kyrios.core.mapper.herosection.HeroSectionMapper;
import dz.kyrios.core.repository.HeroSectionRepository;
import org.springframework.stereotype.Service;

@Service
public class HeroSectionService {

    private final HeroSectionRepository heroSectionRepository;
    private final HeroSectionMapper heroSectionMapper;

    public HeroSectionService(HeroSectionRepository heroSectionRepository,
                              HeroSectionMapper heroSectionMapper) {
        this.heroSectionRepository = heroSectionRepository;
        this.heroSectionMapper = heroSectionMapper;
    }

    public HeroSectionResponse getActiveHeroSection() {
        HeroSection entity = heroSectionRepository.findByActive(Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("No active hero section found"));
        return heroSectionMapper.entityToResponse(entity);
    }
}
