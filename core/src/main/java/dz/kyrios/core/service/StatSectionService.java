package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.dto.statsection.StatSectionResponse;
import dz.kyrios.core.entity.HeroSection;
import dz.kyrios.core.entity.StatSection;
import dz.kyrios.core.mapper.herosection.HeroSectionMapper;
import dz.kyrios.core.mapper.statsection.StatSectionMapper;
import dz.kyrios.core.repository.HeroSectionRepository;
import dz.kyrios.core.repository.StatSectionRepository;
import org.springframework.stereotype.Service;

@Service
public class StatSectionService {

    private final StatSectionRepository statSectionRepository;
    private final StatSectionMapper statSectionMapper;

    public StatSectionService(StatSectionRepository statSectionRepository,
                              StatSectionMapper statSectionMapper) {
        this.statSectionRepository = statSectionRepository;
        this.statSectionMapper = statSectionMapper;
    }

    public StatSectionResponse getActiveStatSection() {
        StatSection entity = statSectionRepository.findByActive(Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("No active hero section found"));
        StatSectionResponse response = statSectionMapper.entityToResponse(entity);
        return response;
    }
}
