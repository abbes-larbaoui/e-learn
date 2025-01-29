package dz.kyrios.core.dto.student;

import dz.kyrios.core.statics.GeneralStatus;

public record StudentResponse(Long id,
                              String firstName,
                              String lastName,
                              GeneralStatus status) {
}
