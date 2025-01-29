package dz.kyrios.core.dto.profile;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseFromAdminService implements Serializable {
    private Long id;
    private Long userId;
    private String userUuid;
}
