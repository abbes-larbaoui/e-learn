package dz.kyrios.core.dto.subjectssection;

import dz.kyrios.core.dto.field.FieldResponse;
import dz.kyrios.core.statics.GeneralStatus;

public record SubjectResponse(Long id,
                              String name,
                              String description,
                              FieldResponse field,
                              String image,
                              String icon,
                              GeneralStatus status) {
}
