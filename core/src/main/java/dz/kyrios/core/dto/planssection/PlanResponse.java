package dz.kyrios.core.dto.planssection;

public record PlanResponse(Long id,
                           String subject,
                           String field,
                           String teacherName,
                           int months,
                           int sessionsPerWeek,
                           double price) {
}
