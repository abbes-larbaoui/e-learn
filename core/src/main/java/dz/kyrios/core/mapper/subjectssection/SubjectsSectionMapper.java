package dz.kyrios.core.mapper.subjectssection;

import dz.kyrios.core.dto.subjectssection.SubjectsSectionResponse;
import dz.kyrios.core.entity.SubjectsSection;

public interface SubjectsSectionMapper {

    SubjectsSectionResponse entityToResponse(SubjectsSection entity);
}
