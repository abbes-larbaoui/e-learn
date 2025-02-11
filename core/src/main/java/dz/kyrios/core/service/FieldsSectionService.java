package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.fieldssection.FieldsSectionResponse;
import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.entity.FieldsSection;
import dz.kyrios.core.entity.SubjectsSection;
import dz.kyrios.core.mapper.fieldssection.FieldsSectionMapper;
import dz.kyrios.core.mapper.subjectssection.SubjectsSectionMapper;
import dz.kyrios.core.repository.FieldsSectionRepository;
import dz.kyrios.core.repository.SubjectsSectionRepository;
import org.springframework.stereotype.Service;

@Service
public class FieldsSectionService {

    private final FieldsSectionRepository fieldsSectionRepository;
    private final FieldsSectionMapper fieldsSectionMapper;

    public FieldsSectionService(FieldsSectionRepository fieldsSectionRepository,
                                FieldsSectionMapper fieldsSectionMapper) {
        this.fieldsSectionRepository = fieldsSectionRepository;
        this.fieldsSectionMapper = fieldsSectionMapper;
    }

    public FieldsSectionResponse getActiveFieldsSection() {
        FieldsSection entity = fieldsSectionRepository.findOneByActive(Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("No active hero section found"));
        return fieldsSectionMapper.entityToResponse(entity);
    }
}
