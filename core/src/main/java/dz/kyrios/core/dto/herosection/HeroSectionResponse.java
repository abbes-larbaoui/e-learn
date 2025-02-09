package dz.kyrios.core.dto.herosection;

import dz.kyrios.core.dto.field.FieldResponse;

import java.util.Set;

public record HeroSectionResponse(Long id, String title, String description, Set<FieldResponse> fields, boolean active) {
}
