package dz.kyrios.core.config.security;

import dz.kyrios.core.dto.profile.ProfileResponseFromAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class SecurityService {

    @Value("${active-profile-uri}")
    private String activeProfileUri;

    public ProfileResponseFromAdminService getUserActiveProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
            Jwt jwt = jwtAuthentication.getToken();

            String url = UriComponentsBuilder.fromUriString(activeProfileUri)
                    .build()
                    .toUriString();

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwt.getTokenValue());

            // Create entity
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            // Send the request
            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<ProfileResponseFromAdminService> response = restTemplate
                        .exchange(url, HttpMethod.GET, entity, ProfileResponseFromAdminService.class);
                return response.getBody();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }
}
