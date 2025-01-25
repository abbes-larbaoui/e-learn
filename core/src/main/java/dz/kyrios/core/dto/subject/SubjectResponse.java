package dz.kyrios.core.dto.subject;

import dz.kyrios.core.dto.field.FieldResponse;
import dz.kyrios.core.statics.GeneralStatus;

public record SubjectResponse(Long id, String name, String description, FieldResponse fieldResponse, GeneralStatus status) {
}
