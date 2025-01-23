package dz.kyrios.core.entity;

import dz.kyrios.core.statics.ProfileType;
import lombok.Data;

@Data
public class Profile {

    private Long id;

    private Long userId;

    private String userUuid;

    private ProfileType profileType;
}
