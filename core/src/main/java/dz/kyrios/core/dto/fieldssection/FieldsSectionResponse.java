package dz.kyrios.core.dto.fieldssection;

import java.util.Set;

public record FieldsSectionResponse(Long id,
                                    String title,
                                    String description,
                                    Set<FieldResponse> fields,
                                    boolean active) {
}
