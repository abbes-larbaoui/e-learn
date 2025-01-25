package dz.kyrios.core.mapper.subject;

import dz.kyrios.core.dto.subject.SubjectRequest;
import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.entity.Subject;

public interface SubjectMapper {

    Subject requestToEntity(SubjectRequest request);

    SubjectResponse entityToResponse(Subject subject);
}
