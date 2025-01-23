package dz.kyrios.core.entity;

import dz.kyrios.core.statics.UserType;
import lombok.Data;

@Data
public class Profile {

    private Long id;

    private Long userId;

    private String userUuid;

    private UserType userType;
}
