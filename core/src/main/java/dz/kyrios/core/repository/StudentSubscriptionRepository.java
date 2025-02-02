package dz.kyrios.core.repository;

import dz.kyrios.core.entity.SessionStartingTime;
import dz.kyrios.core.entity.StudentSubscription;
import dz.kyrios.core.entity.SubscriptionPlan;
import dz.kyrios.core.statics.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.DayOfWeek;

public interface StudentSubscriptionRepository extends JpaRepository<StudentSubscription, Long>, JpaSpecificationExecutor<StudentSubscription> {
    boolean existsBySubscriptionPlanAndStatusAndSessionPlans_SessionDayAndSessionPlans_StartingTime(
            SubscriptionPlan subscriptionPlan, SubscriptionStatus status,
            DayOfWeek sessionDay, SessionStartingTime startingTime);
}
