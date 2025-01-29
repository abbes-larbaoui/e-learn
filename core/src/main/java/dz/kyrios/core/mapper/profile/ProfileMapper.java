package dz.kyrios.core.mapper.profile;

import dz.kyrios.core.dto.profile.ProfileResponseFromAdminService;
import dz.kyrios.core.entity.Profile;

public interface ProfileMapper {

    Profile profileRequestFromAdminServiceToEntity(ProfileResponseFromAdminService request);
}
