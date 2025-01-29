package dz.kyrios.core.mapper.subscriptionplan;

import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanRequest;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanResponse;
import dz.kyrios.core.entity.SubscriptionPlan;

public interface SubscriptionPlanMapper {

    SubscriptionPlan requestToEntity(SubscriptionPlanRequest request);

    SubscriptionPlanResponse entityToResponse(SubscriptionPlan subscriptionPlan);
}
