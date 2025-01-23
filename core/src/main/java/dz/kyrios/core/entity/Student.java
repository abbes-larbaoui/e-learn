package dz.kyrios.core.entity;

import dz.kyrios.core.statics.GeneralStatus;
import lombok.Data;

import java.util.List;

@Data
public class Student {

    private Long id;

    private Profile profile;

    private String name;

    private List<StudentSubscription> subscriptions;

    private GeneralStatus status;
}
