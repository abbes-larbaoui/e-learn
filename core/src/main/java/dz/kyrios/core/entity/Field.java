package dz.kyrios.core.entity;

import dz.kyrios.core.statics.GeneralStatus;
import lombok.Data;

@Data
public class Field {

    private Long id;

    private String name;

    private GeneralStatus status;
}
