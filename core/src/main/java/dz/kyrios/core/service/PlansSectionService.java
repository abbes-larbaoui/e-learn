package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.fieldssection.FieldsSectionResponse;
import dz.kyrios.core.dto.planssection.PlansSectionResponse;
import dz.kyrios.core.entity.FieldsSection;
import dz.kyrios.core.entity.PlansSection;
import dz.kyrios.core.mapper.fieldssection.FieldsSectionMapper;
import dz.kyrios.core.mapper.planssection.PlansSectionMapper;
import dz.kyrios.core.repository.FieldsSectionRepository;
import dz.kyrios.core.repository.PlansSectionRepository;
import org.springframework.stereotype.Service;

@Service
public class PlansSectionService {

    private final PlansSectionRepository plansSectionRepository;
    private final PlansSectionMapper plansSectionMapper;

    public PlansSectionService(PlansSectionRepository plansSectionRepository,
                               PlansSectionMapper plansSectionMapper) {
        this.plansSectionRepository = plansSectionRepository;
        this.plansSectionMapper = plansSectionMapper;
    }

    public PlansSectionResponse getActivePlansSection() {
        PlansSection entity = plansSectionRepository.findOneByActive(Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("No active plans section found"));
        return plansSectionMapper.entityToResponse(entity);
    }
}
