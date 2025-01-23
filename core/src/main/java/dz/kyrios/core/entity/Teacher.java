package dz.kyrios.core.entity;

import dz.kyrios.core.statics.GeneralStatus;
import lombok.Data;

import java.util.List;

@Data
public class Teacher {

    private Long id;

    private Profile profile;

    private String name;

    private List<SubscriptionPlan> subscriptionPlans;

    private GeneralStatus status;
}
