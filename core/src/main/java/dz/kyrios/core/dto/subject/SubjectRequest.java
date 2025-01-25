package dz.kyrios.core.dto.subject;

import dz.kyrios.core.statics.GeneralStatus;

public record SubjectRequest(Long id, String name, String description, Long fieldId, GeneralStatus status) {
}
