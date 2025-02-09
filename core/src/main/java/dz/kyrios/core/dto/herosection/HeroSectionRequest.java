package dz.kyrios.core.dto.herosection;

import dz.kyrios.core.statics.GeneralStatus;

public record HeroSectionRequest(Long id, String name, GeneralStatus status) {
}
