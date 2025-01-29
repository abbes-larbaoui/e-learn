package dz.kyrios.adminservice.dto.profile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseForCore {
    private Long id;
    private Long userId;
    private String userUuid;
}
