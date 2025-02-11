package dz.kyrios.core.dto.planssection;

import java.util.Set;

public record PlansSectionResponse(Long id,
                                   String title,
                                   String description,
                                   Set<PlanResponse> plans,
                                   boolean active) {
}
