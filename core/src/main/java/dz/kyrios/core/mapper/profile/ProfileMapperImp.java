package dz.kyrios.core.mapper.profile;

import dz.kyrios.core.dto.profile.ProfileResponseFromAdminService;
import dz.kyrios.core.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapperImp implements ProfileMapper {

    @Override
    public Profile profileRequestFromAdminServiceToEntity(ProfileResponseFromAdminService request) {
        return null;
    }
}
