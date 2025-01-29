package dz.kyrios.core.dto.teacher;

import dz.kyrios.core.statics.GeneralStatus;

public record TeacherRequest(Long id,
                             String firstName,
                             String lastName,
                             String bio,
                             GeneralStatus status) {
}
