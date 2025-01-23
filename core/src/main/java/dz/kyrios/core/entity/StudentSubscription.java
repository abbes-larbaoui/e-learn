package dz.kyrios.core.entity;

import dz.kyrios.core.statics.GeneralStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentSubscription {

    private Long id;

    private Student student;

    private SubscriptionPlan subscriptionPlan;

    private LocalDateTime subscriptionStartTime;

    private LocalDateTime subscriptionEndTime;

    private GeneralStatus status;
}
