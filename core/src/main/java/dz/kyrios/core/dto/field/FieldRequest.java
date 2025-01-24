package dz.kyrios.core.dto.field;

import dz.kyrios.core.statics.GeneralStatus;

public record FieldRequest(Long id, String name, GeneralStatus status) {
}
