package dz.kyrios.core.mapper.subjectssection;

import dz.kyrios.core.dto.subjectssection.SubjectResponse;
import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.entity.SubjectsSection;
import dz.kyrios.core.mapper.field.FieldMapper;
import dz.kyrios.core.mapper.subject.SubjectMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SubjectsSectionMapperImp implements SubjectsSectionMapper {

    private final SubjectMapper subjectMapper;
    private final FieldMapper fieldMapper;

    public SubjectsSectionMapperImp(SubjectMapper subjectMapper, FieldMapper fieldMapper) {
        this.subjectMapper = subjectMapper;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public SubjectsSectionResponse entityToResponse(SubjectsSection entity) {

        return new SubjectsSectionResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getSubjects().stream().map(subjectsSectionSubject ->
                    new SubjectResponse(
                            subjectsSectionSubject.getSubject().getId(),
                            subjectsSectionSubject.getSubject().getName(),
                            subjectsSectionSubject.getSubject().getDescription(),
                            fieldMapper.entityToResponse(subjectsSectionSubject.getSubject().getField()),
                            subjectsSectionSubject.getImage(),
                            subjectsSectionSubject.getIcon(),
                            subjectsSectionSubject.getSubject().getStatus()
                            )
                ).collect(Collectors.toSet()),
                entity.getActive()
        );
    }
}
