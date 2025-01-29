package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.config.security.SecurityService;
import dz.kyrios.core.dto.profile.ProfileResponseFromAdminService;
import dz.kyrios.core.entity.Profile;
import dz.kyrios.core.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfileService {

    private final SecurityService securityService;

    private final ProfileRepository profileRepository;

    public ProfileService(SecurityService securityService,
                          ProfileRepository profileRepository) {
        this.securityService = securityService;
        this.profileRepository = profileRepository;
    }

    public Profile getCurrentProfile() {
        ProfileResponseFromAdminService profileResponse = securityService.getUserActiveProfile();
        return profileRepository.findById(profileResponse.getId())
                .orElseThrow(() -> new NotFoundException(profileResponse.getId(), "Profile not found with id: "));
    }

    @KafkaListener(id = "profileCreateListener", topics = "profileCreatedTopic")
    public void create(ProfileResponseFromAdminService profileCreatedEvent) {
        Profile profile = new Profile(profileCreatedEvent.getId(), profileCreatedEvent.getUserId(), profileCreatedEvent.getUserUuid());
        Profile createdProfile = profileRepository.save(profile);
        log.info("Profile created with id - {}", createdProfile.getId());
    }

//    @KafkaListener(id = "userCreateListenerTest", topics = "userCreatedTopic")
//    public void create(User user) {
//        log.info("User created with userName - {}", user.getUserName());
//    }
}
