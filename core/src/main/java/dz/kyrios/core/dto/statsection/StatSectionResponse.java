package dz.kyrios.core.dto.statsection;

public record StatSectionResponse(Long id,
                                  String title,
                                  String description,
                                  Long students,
                                  Long teachers,
                                  Long plans,
                                  String support) {
}
