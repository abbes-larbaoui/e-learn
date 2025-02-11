package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.entity.HeroSection;
import dz.kyrios.core.entity.SubjectsSection;
import dz.kyrios.core.mapper.subjectssection.SubjectsSectionMapper;
import dz.kyrios.core.repository.SubjectsSectionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectsSectionService {

    private final SubjectsSectionRepository subjectsSectionRepository;
    private final SubjectsSectionMapper subjectsSectionMapper;

    public SubjectsSectionService(SubjectsSectionRepository subjectsSectionRepository,
                                  SubjectsSectionMapper subjectsSectionMapper) {
        this.subjectsSectionRepository = subjectsSectionRepository;
        this.subjectsSectionMapper = subjectsSectionMapper;
    }

    public SubjectsSectionResponse getActiveSubjectsSection() {
        SubjectsSection entity = subjectsSectionRepository.findOneByActive(Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("No active hero section found"));
        return subjectsSectionMapper.entityToResponse(entity);
    }
}
