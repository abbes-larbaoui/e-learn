package dz.kyrios.core.dto.fieldssection;

import dz.kyrios.core.statics.GeneralStatus;

public record FieldResponse(Long id,
                            String name,
                            String image,
                            String icon,
                            GeneralStatus status) {
}
