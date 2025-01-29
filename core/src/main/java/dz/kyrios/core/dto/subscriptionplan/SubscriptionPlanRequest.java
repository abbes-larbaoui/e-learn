package dz.kyrios.core.dto.subscriptionplan;

import dz.kyrios.core.statics.SubscriptionPlanStatus;

public record SubscriptionPlanRequest(Long id,
                                      Long subjectId,
                                      Integer months,
                                      Integer sessionsPerWeek,
                                      Double price,
                                      SubscriptionPlanStatus status) {
}
