package dz.kyrios.core.mapper.fieldssection;

import dz.kyrios.core.dto.fieldssection.FieldResponse;
import dz.kyrios.core.dto.fieldssection.FieldsSectionResponse;
import dz.kyrios.core.dto.subjectssection.SubjectResponse;
import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.entity.FieldsSection;
import dz.kyrios.core.entity.SubjectsSection;
import dz.kyrios.core.mapper.field.FieldMapper;
import dz.kyrios.core.mapper.subject.SubjectMapper;
import dz.kyrios.core.mapper.subjectssection.SubjectsSectionMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FieldsSectionMapperImp implements FieldsSectionMapper {

    private final SubjectMapper subjectMapper;
    private final FieldMapper fieldMapper;

    public FieldsSectionMapperImp(SubjectMapper subjectMapper, FieldMapper fieldMapper) {
        this.subjectMapper = subjectMapper;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public FieldsSectionResponse entityToResponse(FieldsSection entity) {

        return new FieldsSectionResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getFields().stream().map(fieldsSectionField ->
                    new FieldResponse(
                            fieldsSectionField.getField().getId(),
                            fieldsSectionField.getField().getName(),
                            fieldsSectionField.getImage(),
                            fieldsSectionField.getIcon(),
                            fieldsSectionField.getField().getStatus()
                            )
                ).collect(Collectors.toSet()),
                entity.getActive()
        );
    }
}
