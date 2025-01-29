package dz.kyrios.core.dto.subscriptionplan;

import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.dto.teacher.TeacherResponseForSubscriptionPlan;
import dz.kyrios.core.statics.SubscriptionPlanStatus;

public record SubscriptionPlanResponse(Long id,
                                       TeacherResponseForSubscriptionPlan teacher,
                                       SubjectResponse subject,
                                       Integer months,
                                       Integer sessionsPerWeek,
                                       Double price,
                                       SubscriptionPlanStatus status) {
}
