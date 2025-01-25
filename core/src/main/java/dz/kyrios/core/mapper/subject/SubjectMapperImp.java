package dz.kyrios.core.mapper.subject;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.subject.SubjectRequest;
import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.entity.Field;
import dz.kyrios.core.entity.Subject;
import dz.kyrios.core.mapper.field.FieldMapper;
import dz.kyrios.core.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapperImp implements SubjectMapper {

    private final FieldRepository fieldRepository;

    private final FieldMapper fieldMapper;

    @Autowired
    public SubjectMapperImp(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public Subject requestToEntity(SubjectRequest request) {
        Field field = fieldRepository.findById(request.fieldId())
                .orElseThrow(() -> new NotFoundException(request.fieldId(), "Field not found with id: "));
        return new Subject(request.id(), request.name(), request.description(), field, request.status());
    }

    @Override
    public SubjectResponse entityToResponse(Subject subject) {
        return new SubjectResponse(subject.getId(),
                subject.getName(),
                subject.getDescription(),
                fieldMapper.entityToResponse(subject.getField()),
                subject.getStatus());
    }
}
