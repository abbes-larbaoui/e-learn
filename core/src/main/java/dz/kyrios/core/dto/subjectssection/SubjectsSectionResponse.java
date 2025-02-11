package dz.kyrios.core.dto.subjectssection;

import java.util.Set;

public record SubjectsSectionResponse(Long id,
                                      String title,
                                      String description,
                                      Set<SubjectResponse> subjects,
                                      boolean active) {
}
