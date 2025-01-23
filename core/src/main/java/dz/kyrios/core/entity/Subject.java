package dz.kyrios.core.entity;

import dz.kyrios.core.statics.GeneralStatus;
import lombok.Data;

@Data
public class Subject {

    private Long id;

    private String name;

    private String description;

    private Field field;

    private GeneralStatus status;
}
