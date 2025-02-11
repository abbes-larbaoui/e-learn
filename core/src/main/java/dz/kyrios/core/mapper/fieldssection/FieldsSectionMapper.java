package dz.kyrios.core.mapper.fieldssection;

import dz.kyrios.core.dto.fieldssection.FieldsSectionResponse;
import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.entity.FieldsSection;
import dz.kyrios.core.entity.SubjectsSection;

public interface FieldsSectionMapper {

    FieldsSectionResponse entityToResponse(FieldsSection entity);
}
