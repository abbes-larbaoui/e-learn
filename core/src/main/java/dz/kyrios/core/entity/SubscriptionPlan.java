package dz.kyrios.core.entity;

import dz.kyrios.core.statics.SubscriptionPlanStatus;
import lombok.Data;

import java.util.List;

@Data
public class SubscriptionPlan {

    private Long id;

    private Teacher teacher;

    private List<TeacherWorkingDay> workingDays;

    private int months;
    
    private int sessionsPerWeek;

    private double price;

    private SubscriptionPlanStatus status;
}
