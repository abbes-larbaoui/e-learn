package dz.kyrios.core.dto.student;

import dz.kyrios.core.statics.GeneralStatus;

public record StudentRequest(Long id,
                             String firstName,
                             String lastName,
                             GeneralStatus status) {
}
